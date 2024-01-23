package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByMemberIdAndStartDateTimeBetween(Long memberId, LocalDateTime strStartDateTime, LocalDateTime strEndDateTime);

    @Query("SELECT g FROM Group g WHERE UPPER(g.name) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(g.category) LIKE CONCAT('%', UPPER(:keyword), '%')")
    List<Group> findAllByNameContainingOrCategoryContaining(String keyword);

}
