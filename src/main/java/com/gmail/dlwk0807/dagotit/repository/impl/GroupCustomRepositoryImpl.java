package com.gmail.dlwk0807.dagotit.repository.impl;

import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.entity.QGroup;
import com.gmail.dlwk0807.dagotit.repository.GroupCustomRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gmail.dlwk0807.dagotit.entity.QGroup.group;
import static com.gmail.dlwk0807.dagotit.entity.QGroupAttend.groupAttend;
import static com.gmail.dlwk0807.dagotit.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class GroupCustomRepositoryImpl implements GroupCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Group> findAllByMonth(int month) {
        return query.selectFrom(group)
                .where(group.startDateTime.month().eq(month))
                .fetch();
    }
}
