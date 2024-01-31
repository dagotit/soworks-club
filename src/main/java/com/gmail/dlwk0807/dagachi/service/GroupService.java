package com.gmail.dlwk0807.dagachi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.dlwk0807.dagachi.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.entity.*;
import com.gmail.dlwk0807.dagachi.repository.CategoryRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupFileRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtil;
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

import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAttendRepository groupAttendRepository;
    private final AuthUtil authUtil;
    private final GroupCustomRepositoryImpl groupCustomRepositoryImpl;
    private final GroupImageService groupImageService;
    private final CategoryRepository categoryRepository;
    private final GroupFileRepository groupFileRepository;
    private final GroupFileCloudService groupFileCloudService;
    private final RedisTemplate<String, GroupResent> redisTemplate;

    public GroupResponseDTO saveGroup(GroupSaveRequestDTO requestDto, MultipartFile groupImageFile) throws Exception {

        Member member = authUtil.getCurrentMember();
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

        return of;
    }

    private LocalDateTime parseToFormatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(date, formatter);

    }

    public GroupResponseDTO updateGroup(GroupUpdateRequestDTO requestDto, MultipartFile groupImageFile) {

        Group group = groupRepository.findById(requestDto.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));

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
        group.updateStatus(GroupStatus.valueOf(groupStatusRequestDTO.getStatus()));

        GroupResponseDTO of = GroupResponseDTO.of(group);

        return of;
    }

    public void deleteGroup(GroupDeleteRequestDTO groupDeleteRequestDTO) {
        Group group = groupRepository.findById(groupDeleteRequestDTO.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));

        String deleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
        log.info("기존 이미지 삭제 결과 : {}", deleteResult);

        //delete group 첨부파일 작업필요
        groupFileCloudService.deleteGroupImageFolder(group.getId());

        groupRepository.delete(group);
    }

    public List<GroupResponseDTO> listGroup(GroupListRequestDTO groupListRequestDTO) {
        return groupCustomRepositoryImpl.findAllByFilter(groupListRequestDTO, getCurrentMemberId()).stream()
                .map(GroupResponseDTO::of)
                .collect(Collectors.toList());
    }

    public String updateGroupAttachFile(GroupAttachFileRequestDTO requestDto, List<MultipartFile> groupFiles) {

        Group group = groupRepository.findById(requestDto.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));

        if (!authUtil.isAdmin()) {
            //모임장 체크
            if (!group.getMemberId().equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

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
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
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
        List<GroupResent> result = new ObjectMapper().convertValue(Objects.requireNonNull(zSetOps.reverseRange(setKey(getCurrentMemberId()), 0, -1)),
                new TypeReference<List<GroupResent>>() {});
        return result.stream().collect(Collectors.toList());
    }

}
