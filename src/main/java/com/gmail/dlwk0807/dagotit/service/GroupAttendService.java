package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AuthenticationNotMatchException;
import com.gmail.dlwk0807.dagotit.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagotit.core.exception.DuplicationGroupAttend;
import com.gmail.dlwk0807.dagotit.dto.group.GroupAttendRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagotit.dto.member.MemberAttendResponseDTO;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.GroupAttend;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import com.gmail.dlwk0807.dagotit.repository.impl.MemberCustomRepositoryImpl;
import com.gmail.dlwk0807.dagotit.util.AuthUtil;
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
    private final AuthUtil authUtil;
    private final MemberCustomRepositoryImpl memberCustomRepository;
    private final GroupImageService groupImageService;


    public GroupResponseDTO applyGroupAttend(GroupAttendRequestDTO groupAttendRequestDto, User user) throws Exception {
        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(groupAttendRequestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        long groupId = groupAttendRequestDto.getGroupId();
        long memberId = groupAttendRequestDto.getMemberId();
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

        GroupResponseDTO groupResponseDTO = GroupResponseDTO.of(group);
        groupResponseDTO.updateGroupImg(groupImageService.findImage(groupId));
        return GroupResponseDTO.of(group);
    }

    public List<MemberAttendResponseDTO> listGroupAttend(Long groupId) {
        List<Member> memberList = memberCustomRepository.findAllByGroupId(groupId);
        return memberList.stream().map(v -> MemberAttendResponseDTO.of(v))
                .collect(Collectors.toList());
    }

    public String cancelGroupAttend(GroupAttendRequestDTO groupAttendRequestDto, User user) {
        if (!authUtil.isAdmin()) {
            Long currentMemberId = Long.valueOf(user.getUsername());
            if (!currentMemberId.equals(groupAttendRequestDto.getMemberId())) {
                throw new AuthenticationNotMatchException();
            }
        }

        long groupId = groupAttendRequestDto.getGroupId();
        long memberId = groupAttendRequestDto.getMemberId();
        //참석체크
        if (!groupAttendRepository.existsByGroupIdAndMemberId(groupId, memberId)) {
            throw new CustomRespBodyException("참여하지 않은 모임입니다.");
        }

        GroupAttend groupAttend = groupAttendRepository.findByGroupIdAndMemberId(groupId, memberId).orElseThrow();
        groupAttendRepository.delete(groupAttend);

        return "ok";
    }
}
