package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagotit.repository.GroupRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final GroupRepository groupRepository;

    public List<GroupResponseDTO> search(String keyword) {
        return groupRepository.findAllByNameContainingOrCategoryContaining(keyword).stream().map(GroupResponseDTO::of).collect(Collectors.toList());
    }
}
