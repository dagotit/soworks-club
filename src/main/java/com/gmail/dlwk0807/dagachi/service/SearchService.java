package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.group.GroupResponseDTO;
import com.gmail.dlwk0807.dagachi.repository.GroupRepository;
import com.gmail.dlwk0807.dagachi.repository.impl.GroupCustomRepositoryImpl;
import com.gmail.dlwk0807.dagachi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final GroupCustomRepositoryImpl groupCustomRepositoryImpl;
    private final AuthUtils authUtils;

    public List<GroupResponseDTO> search(String keyword) {
        return groupCustomRepositoryImpl.findAllByNameContainingOrCategoryContaining(keyword, authUtils.getCurrentCompany().getId()).stream()
                .map(GroupResponseDTO::of).collect(Collectors.toList());
    }
}
