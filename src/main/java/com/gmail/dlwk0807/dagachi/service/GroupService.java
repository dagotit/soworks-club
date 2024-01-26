package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagachi.dto.group.*;
import com.gmail.dlwk0807.dagachi.entity.*;
import com.gmail.dlwk0807.dagachi.repository.CategoryRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.MemberRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberId;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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

    public GroupResponseDTO saveGroup(GroupSaveRequestDTO requestDto, MultipartFile groupImageFile) throws Exception {

        Member member = authUtil.getCurrentMember();
        Group group = requestDto.toGroup(member);

        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new CustomRespBodyException("카테고리 관리번호를 확인해주세요"));
        group.updateCategory(category);

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

        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new CustomRespBodyException("카테고리 관리번호를 확인해주세요"));
        group.updateCategory(category);

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
        return of;
    }

}
