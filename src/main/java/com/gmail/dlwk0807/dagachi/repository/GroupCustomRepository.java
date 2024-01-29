package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.dto.group.GroupListRequestDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCustomRepository {
    List<Group> findAllByMonthAndYear(int month, int year);

    List<Group> findAllByFilter(GroupListRequestDTO groupListRequestDTO, Long memberId);

    List<Group> findAllByNameContainingOrCategoryContaining(String keyword);
}
