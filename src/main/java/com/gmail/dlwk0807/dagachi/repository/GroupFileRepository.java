package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.GroupFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupFileRepository extends JpaRepository<GroupFile, Long> {
    List<GroupFile> findAllByGroupId(Long id);
}
