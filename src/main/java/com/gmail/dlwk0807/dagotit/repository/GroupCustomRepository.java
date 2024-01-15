package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCustomRepository {
    List<Group> findAllByMonth(int month, int year);
}
