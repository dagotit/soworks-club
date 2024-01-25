package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroupAttend;
import com.gmail.dlwk0807.dagachi.dto.group.GroupAttendRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberAttendResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupAttend;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.MemberCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberId;


@Service
@RequiredArgsConstructor
@Transactional
public class GroupAttendService {

    private final GroupAttendRepository groupAttendRepository;
    private final GroupRepository groupRepository;
    private final MemberCustomRepositoryImpl memberCustomRepository;
    private final AuthUtil authUtil;


    public GroupResponseDTO applyGroupAttend(GroupAttendRequestDTO groupAttendRequestDto) throws Exception {

        long groupId = groupAttendRequestDto.getGroupId();
        Member member = authUtil.getCurrentMember();
        //중복체크
        if (groupAttendRepository.existsByGroupIdAndMemberId(groupId, member.getId())) {
            throw new DuplicationGroupAttend("이미 모임신청을 하셨습니다.");
        }

        Group group = groupRepository.findById(groupId).orElseThrow();
        GroupAttend groupAttend = GroupAttend.builder()
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        return GroupResponseDTO.of(group);
    }

    public List<MemberAttendResponseDTO> listGroupAttend(Long groupId) {
        List<Member> memberList = memberCustomRepository.findAllByGroupId(groupId);
        return memberList.stream().map(MemberAttendResponseDTO::of)
                .collect(Collectors.toList());
    }

    public String cancelGroupAttend(GroupAttendRequestDTO groupAttendRequestDto) {

        long groupId = groupAttendRequestDto.getGroupId();
        Long currentMemberId = getCurrentMemberId();
        //참석체크
        if (!groupAttendRepository.existsByGroupIdAndMemberId(groupId, currentMemberId)) {
            throw new CustomRespBodyException("참여하지 않은 모임입니다.");
        }

        GroupAttend groupAttend = groupAttendRepository.findByGroupIdAndMemberId(groupId, currentMemberId).orElseThrow();
        groupAttendRepository.delete(groupAttend);

        return "ok";
    }
}
