package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.GroupAttend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupAttendRepository extends JpaRepository<GroupAttend, Long> {
    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);
    Optional<GroupAttend> findByGroupIdAndMemberId(long groupId, long memberId);
}
