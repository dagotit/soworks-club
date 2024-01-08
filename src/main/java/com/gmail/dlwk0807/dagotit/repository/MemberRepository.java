package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndName(String email, String name);

}
