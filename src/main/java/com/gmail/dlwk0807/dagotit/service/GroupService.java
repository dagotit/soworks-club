package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagotit.dto.GroupRequestDto;
import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.GroupAttend;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.gmail.dlwk0807.dagotit.util.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAttendRepository groupAttendRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Group saveGroup(GroupRequestDto requestDto, User user) {
        String memberId = user.getUsername();
        requestDto.setCurrentMemberId(memberId);

        Group group = requestDto.toGroup();

        LocalDateTime startDateTime = parseToFormatDate(requestDto.getStrStartDateTime());
        LocalDateTime endDateTime = parseToFormatDate(requestDto.getStrEndDateTime());
        List<Group> groupList = groupRepository.findByMemberIdAndStartDateTimeBetween(requestDto.getMemberId(), startDateTime, endDateTime);
        if (groupList.size() > 0) {
            throw new DuplicationGroup();
        }
        groupRepository.save(group);

        Member member = memberRepository.findById(Long.parseLong(memberId)).orElseThrow();
        //모임 만들때 모임장 참여인원에 저장
        GroupAttend groupAttend = GroupAttend.builder()
                .attendYn("Y")
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        return group;
    }

    private LocalDateTime parseToFormatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(date, formatter);

    }

    public Group updateGroup(GroupRequestDto requestDto, User user) {
        if (!memberService.isAdmin()) {
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

    public void deleteGroup(GroupRequestDto requestDto, User user) {
        if (!memberService.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(requestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        groupRepository.deleteById(requestDto.getId());
    }

}
