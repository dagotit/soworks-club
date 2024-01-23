package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.GroupAttend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupAttendRepository extends JpaRepository<GroupAttend, Long> {
    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);
    Optional<GroupAttend> findByGroupIdAndMemberId(long groupId, long memberId);
}
