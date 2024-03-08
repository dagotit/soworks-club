package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByMemberIdAndStartDateTimeBetween(Long memberId, LocalDateTime strStartDateTime, LocalDateTime strEndDateTime);

//    @Query("SELECT g FROM Group g WHERE UPPER(g.name) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(g.categories) LIKE CONCAT('%', UPPER(:keyword), '%')")
    @Query("SELECT g FROM Group g WHERE UPPER(g.name) LIKE CONCAT('%', UPPER(:keyword), '%') OR g.categories IN (SELECT c.id FROM Category c WHERE UPPER(c.name) LIKE CONCAT('%', UPPER(:keyword), '%'))")
    List<Group> findAllByNameContainingOrCategoryContaining(String keyword);

    Optional<Group> findByIdAndCompany(Long groupId, Company currentCompany);
}
