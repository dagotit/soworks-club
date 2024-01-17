package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagotit.dto.group.GroupAttachFileRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.GroupAttend;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagotit.util.FileUtil.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAttendRepository groupAttendRepository;
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;
    private final GroupCustomRepositoryImpl groupCustomRepositoryImpl;
    private final GroupImageService groupImageService;

    public GroupResponseDTO saveGroup(GroupRequestDTO requestDto, MultipartFile groupImageFile, User user) throws Exception {

        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        Long memberId = Long.valueOf(user.getUsername());
        requestDto.setCurrentMemberId(memberId);

        Group group = requestDto.toGroup();

        LocalDateTime startDateTime = parseToFormatDate(requestDto.getStrStartDateTime());
        LocalDateTime endDateTime = parseToFormatDate(requestDto.getStrEndDateTime());
        List<Group> groupList = groupRepository.findByMemberIdAndStartDateTimeBetween(memberId, startDateTime, endDateTime);
        if (groupList.size() > 0) {
            throw new DuplicationGroup();
        }

        //모임 이미지 저장
        if (groupImageFile != null && !groupImageFile.isEmpty()) {
            String imageName = groupImageService.uploadGroupImage(groupImageFile);
            group.updateImageName(imageName);
        }

        groupRepository.save(group);

        Member member = memberRepository.findById(memberId).orElseThrow();
        //모임 만들때 모임장 참여인원에 저장
        GroupAttend groupAttend = GroupAttend.builder()
                .attendYn("Y")
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        GroupResponseDTO groupResponseDTO = GroupResponseDTO.of(group);

        return groupResponseDTO;
    }

    private LocalDateTime parseToFormatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(date, formatter);

    }

    public Group updateGroup(GroupRequestDTO requestDto, MultipartFile groupImageFile, User user) {
        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Group group = groupRepository.findById(requestDto.getId()).orElseThrow();

        //모임 이미지 저장, 기존이미지 삭제
        if (groupImageFile != null && !groupImageFile.isEmpty()) {
            String imageName = groupImageService.uploadGroupImage(groupImageFile);
            String deleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
            log.info("기존 이미지 삭제 결과 : {}", deleteResult);
            group.updateImageName(imageName);
        }

        group.update(requestDto);
        return group;
    }

    public void deleteGroup(GroupRequestDTO requestDto, User user) {
        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Group group = groupRepository.findById(requestDto.getId()).orElseThrow();

        String deleteResult = groupImageService.deleteGroupImage(group.getGroupImage());
        log.info("기존 이미지 삭제 결과 : {}", deleteResult);

        //delete group 첨부파일 작업필요

        groupRepository.deleteById(requestDto.getId());
    }



    public List<GroupResponseDTO> listGroup(int month, int year) {
        return groupCustomRepositoryImpl.findAllByMonth(month, year).stream()
                .map(o -> GroupResponseDTO.of(o)).collect(Collectors.toList());
    }

    public String updateGroupAttachFile(GroupAttachFileRequestDTO requestDto, List<MultipartFile> groupFiles, User user) {

        Group group = groupRepository.findById(Long.valueOf(requestDto.getGroupId())).orElseThrow();

        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
            //모임장 체크
            if (!group.getMemberId().equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        //파일 업로드


        return "ok";
    }

    public GroupResponseDTO info(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        return GroupResponseDTO.of(group);
    }

    public GroupResponseDTO joinGroup(Long groupId, User user) {
        Long memberId = Long.valueOf(user.getUsername());
        Group group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        //참석체크
        if (groupAttendRepository.existsByGroupIdAndMemberId(groupId, memberId)) {
            throw new CustomRespBodyException("이미 참여한 모임 입니다.");
        }

        GroupAttend groupAttend = GroupAttend.builder()
                .attendYn("Y")
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        return GroupResponseDTO.of(group);
    }
}
