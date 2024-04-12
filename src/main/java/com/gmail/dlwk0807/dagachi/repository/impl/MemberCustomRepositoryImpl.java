package com.gmail.dlwk0807.dagachi.repository.impl;

import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.MemberCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gmail.dlwk0807.dagachi.entity.QGroupAttend.*;
import static com.gmail.dlwk0807.dagachi.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Member> findAllByGroupId(Long groupId) {
        return query.selectFrom(member)
                .join(groupAttend).on(groupAttend.member.id.eq(member.id))
                .where(groupAttend.group.id.eq(groupId))
                .fetch();
    }

    @Override
    public List<Member> findAllByNameAndEmailContaining(String name, String email) {
        return query.selectFrom(member)
                .where(containingName(name), containingEmail(email))
                .fetch();
    }

    BooleanExpression containingName(String name) {
        return name != null ? member.name.contains(name) : null;
    }

    BooleanExpression containingEmail(String email) {
        return email != null ? member.email.contains(email) : null;
    }
}
