package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.core.exception.CustomRespBodyException;
import com.gmail.dlwk0807.dagachi.core.exception.DuplicationGroupAttend;
import com.gmail.dlwk0807.dagachi.dto.group.GroupAttendRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagachi.dto.member.MemberAttendResponseDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupAttend;
import com.gmail.dlwk0807.dagachi.entity.GroupStatus;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.GroupAttendRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.MemberCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtils.getCurrentMemberId;


@Service
@RequiredArgsConstructor
@Transactional
public class GroupAttendService {

    private final GroupAttendRepository groupAttendRepository;
    private final GroupRepository groupRepository;
    private final MemberCustomRepositoryImpl memberCustomRepository;
    private final AuthUtils authUtils;


    public GroupResponseDTO applyGroupAttend(GroupAttendRequestDTO groupAttendRequestDto) throws Exception {

        long groupId = groupAttendRequestDto.getGroupId();
        Member member = authUtils.getCurrentMember();
        //중복체크
        if (groupAttendRepository.existsByGroupIdAndMemberId(groupId, member.getId())) {
            throw new DuplicationGroupAttend("이미 모임신청을 하셨습니다.");
        }

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

        //모임 최대인원 확인
        Long groupMaxNum = group.getGroupMaxNum();
        int attendNum = group.getGroupAttendList().size();
        if (groupMaxNum.compareTo((long) attendNum) <= 0) {
            throw new CustomRespBodyException("인원수가 모두 찼습니다.");
        }
        if (groupMaxNum.equals((long) (attendNum + 1))) {
            group.updateStatus(GroupStatus.FULL);
        }

        GroupAttend groupAttend = GroupAttend.builder()
                .group(group)
                .member(member)
                .build();
        groupAttendRepository.save(groupAttend);

        group.getGroupAttendList().add(groupAttend);

        return GroupResponseDTO.of(group);
    }

    public List<MemberAttendResponseDTO> listGroupAttend(Long groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("모임정보가 없습니다."));
        authUtils.checkDiffCompany(group);

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

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new CustomRespBodyException("존재하지 않는 모임입니다."));
        if (currentMemberId.equals(group.getMemberId())) {
            throw new CustomRespBodyException("모임장은 참여 취소를 할 수 없습니다.");
        }

        authUtils.checkDiffCompany(group);

        GroupAttend groupAttend = groupAttendRepository.findByGroupIdAndMemberId(groupId, currentMemberId).orElseThrow(() -> new CustomRespBodyException("참여하지 않은 모임입니다."));
        groupAttendRepository.delete(groupAttend);

        //모임 상태 변경
        if (GroupStatus.FULL.equals(group.getStatus())) {
            group.updateStatus(GroupStatus.WAITING);
        }

        group.getGroupAttendList().remove(groupAttend);

        return "ok";
    }

}
