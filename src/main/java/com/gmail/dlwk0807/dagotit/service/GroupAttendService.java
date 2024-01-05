package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroup;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroupAttend;
import com.gmail.dlwk0807.dagotit.dto.GroupAttendRequestDto;
import com.gmail.dlwk0807.dagotit.dto.GroupRequestDto;
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

@Service
@RequiredArgsConstructor
@Transactional
public class GroupAttendService {

    private final GroupAttendRepository groupAttendRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Group applyGroup(GroupAttendRequestDto groupAttendRequestDto, User user) {
        if (!memberService.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(groupAttendRequestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        long groupId = Long.parseLong(groupAttendRequestDto.getGroupId());
        long memberId = Long.parseLong(groupAttendRequestDto.getMemberId());
        //중복체크
        if (groupAttendRepository.existsByGroupIdAndMemberId(groupId, memberId)) {
            throw new DuplicationGroupAttend("이미 모임신청을 하셨습니다.");
        }

        Group group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        GroupAttend groupAttend = GroupAttend.builder()
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        return group;
    }

}
