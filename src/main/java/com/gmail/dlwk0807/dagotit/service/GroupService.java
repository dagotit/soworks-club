package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroup;
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
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.gmail.dlwk0807.dagotit.util.FileUtil.deleteFile;
import static com.gmail.dlwk0807.dagotit.util.FileUtil.getImage;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAttendRepository groupAttendRepository;
    private final MemberRepository memberRepository;
    private final AuthUtil authUtil;
    private final GroupCustomRepositoryImpl groupCustomRepositoryImpl;
    private final GroupImageService groupImageService;

    @Value("${file.groupImagePath}")
    private String uploadFolder;

    public GroupResponseDTO saveGroup(GroupRequestDTO requestDto, User user) throws Exception {
        String memberId = user.getUsername();
        requestDto.setCurrentMemberId(memberId);

        Group group = requestDto.toGroup();

        LocalDateTime startDateTime = parseToFormatDate(requestDto.getStrStartDateTime());
        LocalDateTime endDateTime = parseToFormatDate(requestDto.getStrEndDateTime());
        List<Group> groupList = groupRepository.findByMemberIdAndStartDateTimeBetween(requestDto.getMemberId(), startDateTime, endDateTime);
        if (groupList.size() > 0) {
            throw new DuplicationGroup();
        }
        //모임 이미지 저장
        String imageName = groupImageService.uploadGroupImage(requestDto);
        group.updateImageName(imageName);
        groupRepository.save(group);

        Member member = memberRepository.findById(Long.parseLong(memberId)).orElseThrow();
        //모임 만들때 모임장 참여인원에 저장
        GroupAttend groupAttend = GroupAttend.builder()
                .attendYn("Y")
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        GroupResponseDTO groupResponseDTO = GroupResponseDTO.of(group);
        groupResponseDTO.updateGroupImg(getImage(uploadFolder + imageName));

        return groupResponseDTO;
    }

    private LocalDateTime parseToFormatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(date, formatter);

    }

    public Group updateGroup(GroupRequestDTO requestDto, User user) {
        if (!authUtil.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        LocalDateTime startDateTime = parseToFormatDate(requestDto.getStrStartDateTime());
        LocalDateTime endDateTime = parseToFormatDate(requestDto.getStrEndDateTime());
        List<Group> groupList = groupRepository.findByMemberIdAndStartDateTimeBetween(requestDto.getMemberId(), startDateTime, endDateTime);
        if (groupList.size() > 0) {
            throw new DuplicationGroup();
        }

        Group group = groupRepository.findById(requestDto.getId()).orElseThrow();
        group.update(requestDto);
        return group;
    }

    public void deleteGroup(GroupRequestDTO requestDto, User user) {
        if (!authUtil.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }
        Group group = groupRepository.findById(requestDto.getId()).orElseThrow();

        String oldFileName = group.getGroupImgName();
        //기본이미지일 경우 업데이트만하고 삭제하지 않는다.
        if (!"anonymous.png".equals(oldFileName)) {
            // 기존 파일 삭제
            deleteFile(uploadFolder + oldFileName);
        }

        groupRepository.deleteById(requestDto.getId());
    }

    public List<Group> listGroup(int month) {
        return groupCustomRepositoryImpl.findAllByMonth(month);
    }

}
