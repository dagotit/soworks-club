package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroupAttend;
import com.gmail.dlwk0807.dagotit.dto.GroupAttendRequestDto;
import com.gmail.dlwk0807.dagotit.dto.GroupResponseDto;
import com.gmail.dlwk0807.dagotit.dto.MemberAttendResponseDto;
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

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class GroupAttendService {

    private final GroupAttendRepository groupAttendRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MemberCustomRepositoryImpl memberCustomRepository;

    public GroupResponseDto applyGroupAttend(GroupAttendRequestDto groupAttendRequestDto, User user) {
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

        return GroupResponseDto.of(group);
    }

    public List<MemberAttendResponseDto> listGroupAttend(Long groupId) {
        List<Member> memberList = memberCustomRepository.findAllByGroupId(groupId);
        return memberList.stream().map(v -> MemberAttendResponseDto.of(v))
                .collect(Collectors.toList());
    }

    public String cancelGroupAttend(GroupAttendRequestDto groupAttendRequestDto, User user) {
        if (!memberService.isAdmin()) {
            String currentMemberId = user.getUsername();
            if (!currentMemberId.equals(groupAttendRequestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        long groupId = Long.parseLong(groupAttendRequestDto.getGroupId());
        long memberId = Long.parseLong(groupAttendRequestDto.getMemberId());
        //참석체크
        if (!groupAttendRepository.existsByGroupIdAndMemberId(groupId, memberId)) {
            throw new CustomRespBodyException("참여하지 않은 모임입니다.");
        }

        GroupAttend groupAttend = groupAttendRepository.findByGroupIdAndMemberId(groupId, memberId).orElseThrow();
        groupAttendRepository.delete(groupAttend);

        return "ok";
    }
}
