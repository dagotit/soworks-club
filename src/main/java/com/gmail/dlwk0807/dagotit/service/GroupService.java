package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.GroupRequestDto;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.repository.AttendanceRepository;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;

    public void saveGroup(GroupRequestDto groupRequestDto) {
        Group group = groupRequestDto.toGroup();
        if (repo)
        groupRepository.save(group);
    }
}
