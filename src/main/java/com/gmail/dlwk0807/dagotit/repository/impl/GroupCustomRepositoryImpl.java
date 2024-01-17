package com.gmail.dlwk0807.dagotit.repository.impl;

import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.entity.GroupStatus;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.entity.QGroup;
import com.gmail.dlwk0807.dagotit.repository.GroupCustomRepository;
import com.gmail.dlwk0807.dagotit.repository.MemberCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public List<Group> findAllByMonth(int month, int year) {
        return query.selectFrom(group)
                .where(monthEq(month), yearEq(year))
                .fetch();
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null? group.startDateTime.year().eq(year) : null;
    }

    private BooleanExpression monthEq(Integer month) {
        return month != null? group.startDateTime.month().eq(month) : null;
    }

    private BooleanExpression statusNe(String status) {
        return status != null? group.status.ne(GroupStatus.valueOf(status)) : null;
    }

//    private BooleanExpression groupIn(Long groupId) {
//        return groupId != null? group.startDateTime.month().eq(groupId) : null;
//    }

    /**
     * 전체보기 -> parameter : month 만
     * 진행중인모임 -> status
     */
}
