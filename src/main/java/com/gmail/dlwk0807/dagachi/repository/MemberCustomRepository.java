package com.gmail.dlwk0807.dagachi.repository;

import com.gmail.dlwk0807.dagachi.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCustomRepository {
    List<Member> findAllByGroupId(Long groupId);
    List<Member> findAllByNameContaining(String name);
}
