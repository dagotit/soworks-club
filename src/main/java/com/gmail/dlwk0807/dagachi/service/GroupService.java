package com.gmail.dlwk0807.dagachi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagachi.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.entity.*;
import com.gmail.dlwk0807.dagachi.repository.*;
import com.gmail.dlwk0807.dagachi.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagachi.entity.CmnScore.*;
import static com.gmail.dlwk0807.dagachi.util.FileUtils.validateImageFile;
import static com.gmail.dlwk0807.dagachi.util.FileUtils.validateImageFiles;
import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAttendRepository groupAttendRepository;
    private final AuthUtils authUtils;
    private final GroupCustomRepositoryImpl groupCustomRepositoryImpl;
    private final GroupImageService groupImageService;
    private final CategoryRepository categoryRepository;
    private final GroupFileRepository groupFileRepository;
    private final MemberRepository memberRepository;
    private final GroupFileCloudService groupFileCloudService;
    private final RedisTemplate<String, GroupResent> redisTemplate;
    private final TitleRepository titleRepository;

    public GroupResponseDTO saveGroup(GroupSaveRequestDTO requestDto, MultipartFile groupImageFile) throws Exception {

        Member member = authUtils.getCurrentMember();
        Group group = requestDto.toGroup(member);

        List<Category> categories = requestDto.getCategoryIds().stream()
                .map(o -> categoryRepository.findById(o).orElseThrow(() -> new CustomRespBodyException("카테고리 관리번호를 확인해주세요")))
                .collect(Collectors.toList());
        group.updateCategory(categories);

        LocalDateTime startDateTime = parseToFormatDate(requestDto.getStrStartDateTime());
        LocalDateTime endDateTime = parseToFormatDate(requestDto.getStrEndDateTime());
        List<Group> groupList = groupRepository.findByMemberIdAndStartDateTimeBetween(member.getId(), startDateTime, endDateTime);
        if (groupList.size() > 0) {
            throw new DuplicationGroup();
        }


        //모임 이미지 저장
        if (groupImageFile != null && !groupImageFile.isEmpty()) {
            //파일 확장자 체크
            validateImageFile(groupImageFile);
            String imageName = groupImageService.uploadGroupImage(groupImageFile);
            group.updateImageName(imageName);
        }

        groupRepository.save(group);

        //모임 만들때 모임장 참여인원에 저장
        GroupAttend groupAttend = GroupAttend.builder()
                .attendYn("Y")
                .group(group)
                .member(member)
                .build();
        GroupAttend attend = groupAttendRepository.save(groupAttend);
        group.getGroupAttendList().add(attend);

        GroupResponseDTO of = GroupResponseDTO.of(group);

        member.addScore(MAKE_GROUP.getScore());

        return of;
    }

    private LocalDateTime parseToFormatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(date, formatter);

    }

    public GroupResponseDTO updateGroup(GroupUpdateRequestDTO requestDto, MultipartFile groupImageFile) {

        Group group = groupRepository.findById(requestDto.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

        if (!authUtils.isAdmin()) {
            //모임장 체크
            if (!group.getMemberId().equals(getCurrentMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        List<Category> categories = requestDto.getCategoryIds().stream()
                .map(o -> categoryRepository.findById(o).orElseThrow(() -> new CustomRespBodyException("카테고리 관리번호를 확인해주세요")))
                .collect(Collectors.toList());
        group.updateCategory(categories);

        //모임 이미지 저장, 기존이미지 삭제
        if (groupImageFile != null && !groupImageFile.isEmpty()) {
            String imageName = groupImageService.uploadGroupImage(groupImageFile);
            String deleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
            log.info("기존 이미지 삭제 결과 : {}", deleteResult);
            group.updateImageName(imageName);
        }
        group.update(requestDto);

        GroupResponseDTO of = GroupResponseDTO.of(group);

        return of;
    }

    public GroupResponseDTO updateGroupStatus(GroupStatusRequestDTO groupStatusRequestDTO) {

        Group group = groupRepository.findById(groupStatusRequestDTO.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

        if (!authUtils.isAdmin()) {
            //모임장 체크
            if (!group.getMemberId().equals(getCurrentMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        GroupStatus newStatus = GroupStatus.valueOf(groupStatusRequestDTO.getStatus());

        /**
         * 1. 모임 종료(DONE)시점에 참가 "Y"인 인원 가산
         * 2. 해당 카테고리 횟수 체크해서 타이틀(칭호) 발급
         */
        if (newStatus == GroupStatus.DONE && group.getStatus() != GroupStatus.DONE) {
            updateCompleteGroupScore(group);
            checkTitles(group);
        }

        group.updateStatus(newStatus);
        GroupResponseDTO of = GroupResponseDTO.of(group);

        return of;
    }

    private void updateCompleteGroupScore(Group group) {
        for (GroupAttend attendee : group.getGroupAttendList()) {
            if ("Y".equals(attendee.getAttendYn())) {
                Member member = attendee.getMember();
                member.addScore(CmnScore.COMPLETE_GROUP.getScore());
            }
        }
    }

    /**
     * 칭호획득 관련 논의 후 작업해야됨
     * @param group
     */
    private void checkTitles(Group group) {
        for (GroupAttend attendee : group.getGroupAttendList()) {
            if ("Y".equals(attendee.getAttendYn())) {
                Member member = attendee.getMember();
                //group category로 해당 타이틀 조회
//                titleRepository.findByCategory(group.)

            }
        }
    }

    public void deleteGroup(GroupDeleteRequestDTO groupDeleteRequestDTO) {
        Group group = groupRepository.findById(groupDeleteRequestDTO.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

        if (!authUtils.isAdmin()) {
            //모임장 체크
            if (!group.getMemberId().equals(getCurrentMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }
        //모임관련 클라우드파일 삭제
        String imageDeleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
        log.info("기존 이미지 삭제 결과 : {}", imageDeleteResult);

        String attachDeleteResult = groupFileCloudService.deleteGroupImageFolder(group.getId());
        log.info("기존 첨부파일 삭제 결과 : {}", attachDeleteResult);

        //모임장 가산점 회수
        Member member = memberRepository.findById(group.getMemberId()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
        member.addScore(FAIL_GROUP.getScore());

        groupRepository.delete(group);
    }

    public List<GroupResponseDTO> listGroup(GroupListRequestDTO groupListRequestDTO) {
        return groupCustomRepositoryImpl.findAllByFilter(groupListRequestDTO, getCurrentMemberId(), authUtils.getCurrentCompany().getId()).stream()
                .map(GroupResponseDTO::of)
                .collect(Collectors.toList());
    }

    public String updateGroupAttachFile(GroupAttachFileRequestDTO requestDto, List<MultipartFile> groupFiles) {

        Group group = groupRepository.findById(requestDto.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

        if (!authUtils.isAdmin()) {
            //모임장 체크
            if (!group.getMemberId().equals(getCurrentMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        //파일 확장자 체크
        validateImageFiles(groupFiles);

        //파일 업로드
        for(MultipartFile file : groupFiles) {
            String originalName = file.getOriginalFilename();
            long size = file.getSize();

            String saveName = groupFileCloudService.uploadGroupFile(file, group.getId());

            GroupFile groupFile = GroupFile.builder()
                    .group(group)
                    .size(size)
                    .saveName(saveName)
                    .originalName(originalName)
                    .build();
            groupFileRepository.save(groupFile);
        }

        return "ok";
    }

    public GroupResponseDTO info(Long groupId) {
        Group group = groupRepository.findByIdAndCompany(groupId, authUtils.getCurrentCompany()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);
        GroupResponseDTO of = GroupResponseDTO.of(group);

        //최근 본 모임 저장 [redis]
        ZSetOperations<String, GroupResent> zSetOps = redisTemplate.opsForZSet();
        String key = setKey(getCurrentMemberId());

        Long size = zSetOps.size(key);

        if (size < 4) {
            GroupResent groupResent = GroupResent.of(group);
            //4개 미만이면 redis 저장
            zSetOps.add(key, groupResent, new java.util.Date().getTime()); // score은 타임스탬프(최신 읽은 순대로 정렬위해)
            redisTemplate.expireAt(key, Date.from(ZonedDateTime.now().plusDays(3).toInstant())); // 유효기간
        }

        return of;
    }

    private String setKey(Long memberId){
        return "userIdx::" + memberId;
    }

    public List<GroupResent> recentList() {

        //최근 본 모임 조회 [redis]
        ZSetOperations<String, GroupResent> zSetOps = redisTemplate.opsForZSet();
        Set<GroupResent> groupResents = zSetOps.reverseRange(setKey(getCurrentMemberId()), 0, -1);
        List<GroupResent> result = new ObjectMapper().convertValue(Objects.requireNonNull(groupResents),
                new TypeReference<>(){});
        return result.stream().collect(Collectors.toList());
    }

    private String processAttendYn(Long groupId, Long joinMemberId, String attendYn) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("존재하지 않는 모임입니다."));

        Long currentMemberId = getCurrentMemberId();
        Long groupMasterId = group.getMemberId();

        if (!authUtils.isAdmin()) {
            if (!groupMasterId.equals(currentMemberId)) {
                throw new AuthenticationNotMatchException();
            }
        }

        authUtils.checkDiffCompany(group);

        GroupAttend groupAttend = groupAttendRepository.findByGroupIdAndMemberId(groupId, joinMemberId).orElseThrow(() -> new CustomRespBodyException("참여하지 않은 모임입니다."));
        groupAttend.updateAttendYn(attendYn);

        return "참가여부 : " + attendYn;
    }

    public String joinDone(GroupAttendYnRequestDTO groupAttendYnRequestDTO) {
        return processAttendYn(groupAttendYnRequestDTO.getGroupId(), groupAttendYnRequestDTO.getMemberId(), "Y");
    }

    public String joinFail(GroupAttendYnRequestDTO groupAttendYnRequestDTO) {
        return processAttendYn(groupAttendYnRequestDTO.getGroupId(), groupAttendYnRequestDTO.getMemberId(), "N");
    }

    public void setFailAndMinusScore() {
        //종료일이랑 지금이랑 일주일 이상 차이나는 모임 조회
        //현재 일시각에서 7일 전
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<GroupStatus> statuses = List.of(GroupStatus.valueOf("DONE"), GroupStatus.valueOf("FAIL"));
        List<Group> expiredGroups = groupRepository.findAllByStatusNotInAndEndDateTimeBefore(statuses, oneWeekAgo);

        for (Group expiredGroup : expiredGroups) {
            expiredGroup.updateStatus(GroupStatus.FAIL);
            Member member = memberRepository.findById(expiredGroup.getMemberId()).orElseThrow(() -> new CustomRespBodyException("회원이 존재하지 않습니다."));
            member.addScore(FAIL_GROUP.getScore());
        }
    }


}
