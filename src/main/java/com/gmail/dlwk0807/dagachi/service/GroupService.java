package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.entity.*;
import com.gmail.dlwk0807.dagachi.repository.CategoryRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
    private final RedisTemplate redisTemplate;

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
        groupAttendRepository.save(groupAttend);

        GroupResponseDTO of = GroupResponseDTO.of(group);
        of.updateMasterYn(getCurrentMemberId().equals(of.getMemberId()) ? "Y" : "N");

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
        of.updateMasterYn(getCurrentMemberId().equals(of.getMemberId()) ? "Y" : "N");

        return of;
    }

    public GroupResponseDTO updateGroupStatus(GroupStatusRequestDTO groupStatusRequestDTO) {

        Group group = groupRepository.findById(groupStatusRequestDTO.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        group.updateStatus(GroupStatus.valueOf(groupStatusRequestDTO.getStatus()));

        GroupResponseDTO of = GroupResponseDTO.of(group);
        of.updateMasterYn(getCurrentMemberId().equals(of.getMemberId()) ? "Y" : "N");

        return of;
    }

    public void deleteGroup(GroupDeleteRequestDTO groupDeleteRequestDTO) {
        Group group = groupRepository.findById(groupDeleteRequestDTO.getGroupId()).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));

        String deleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
        log.info("기존 이미지 삭제 결과 : {}", deleteResult);

        //delete group 첨부파일 작업필요

        groupRepository.delete(group);
    }

    public List<GroupResponseDTO> listGroup(GroupListRequestDTO groupListRequestDTO) {
        return groupCustomRepositoryImpl.findAllByFilter(groupListRequestDTO, getCurrentMemberId()).stream()
                .map(o -> {
                    GroupResponseDTO of = GroupResponseDTO.of(o);
                    of.updateMasterYn(getCurrentMemberId().equals(o.getMemberId()) ? "Y" : "N");
                    return of;
                })
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


        return "ok";
    }

    public GroupResponseDTO info(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        GroupResponseDTO of = GroupResponseDTO.of(group);
        of.updateMasterYn(getCurrentMemberId().equals(group.getMemberId()) ? "Y" : "N");

        //최근 본 모임 저장 [redis]
        ListOperations<String, GroupResent> listOperations = redisTemplate.opsForList();
        String key = "userIdx::" + getCurrentMemberId();
        long size = listOperations.size(key) == null ? 0 : listOperations.size(key); // NPE 체크해야함.

        if (size < 3) {
            GroupResent groupResent = GroupResent.of(group);
            groupResent.updateMasterYn(getCurrentMemberId().equals(group.getMemberId()) ? "Y" : "N");
            //3개 미만이면 redis 저장
            redisTemplate.opsForList().leftPush(key, groupResent);
            redisTemplate.expireAt(key, Date.from(ZonedDateTime.now().plusDays(3).toInstant()));
        }

        return of;
    }

}
