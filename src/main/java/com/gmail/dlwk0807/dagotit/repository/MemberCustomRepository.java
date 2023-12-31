package com.gmail.dlwk0807.dagotit.repository;

import com.gmail.dlwk0807.dagotit.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCustomRepository {
    List<Member> findAllByGroupId(Long groupId);
}
