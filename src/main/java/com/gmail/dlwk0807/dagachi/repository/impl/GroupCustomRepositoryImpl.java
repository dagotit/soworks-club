package com.gmail.dlwk0807.dagachi.repository.impl;

import com.gmail.dlwk0807.dagachi.dto.group.GroupListRequestDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupStatus;
import com.gmail.dlwk0807.dagachi.entity.QCategory;
import com.gmail.dlwk0807.dagachi.repository.GroupCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.gmail.dlwk0807.dagachi.entity.QCategory.category;
import static com.gmail.dlwk0807.dagachi.entity.QGroup.group;
import static com.gmail.dlwk0807.dagachi.entity.QGroupAttend.groupAttend;

@Repository
@RequiredArgsConstructor
public class GroupCustomRepositoryImpl implements GroupCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Group> findAllByMonthAndYear(int month, int year) {
        return query.selectFrom(group)
                .where(monthEq(month), yearEq(year))
                .fetch();
    }

    //    @Query("SELECT g FROM Group g WHERE UPPER(g.name) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(g.categories) LIKE CONCAT('%', UPPER(:keyword), '%')")
//    @Query("SELECT g FROM Group g WHERE UPPER(g.name) LIKE CONCAT('%', UPPER(:keyword), '%') OR g.categories IN (SELECT c.id FROM Category c WHERE UPPER(c.name) LIKE CONCAT('%', UPPER(:keyword), '%'))")
    @Override
    public List<Group> findAllByNameContainingOrCategoryContaining(String keyword) {
        return query.selectFrom(group)
                .where(searchAll(keyword))
                .fetch();
    }


    @Override
    public List<Group> findAllByFilter(GroupListRequestDTO dto, Long memberId) {

        return query.selectFrom(group)
                .where(statusNotDone(dto.getStatusNotDone())
                        , dateBetween(dto.getStYear(), dto.getStMonth(), dto.getEndYear(), dto.getEndMonth(), dto.getFindDate())
                        , makeOnly(dto.getMakeOnly(), memberId)
                        , joinOnly(dto.getJoinOnly(), memberId)
                )
                .fetch();
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null? group.startDateTime.year().eq(year) : null;
    }

    private BooleanExpression monthEq(Integer month) {
        return month != null ? group.startDateTime.month().eq(month) : null;
    }

    private BooleanExpression dateBetween(Integer stYear, Integer stMonth, Integer endYear, Integer endMonth, Integer findDate) {
        LocalDateTime stDate = LocalDateTime.of(stYear, stMonth, 1, 0, 0);
        LocalDate tmpEndDate = LocalDate.of(endYear, endMonth, 1);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, tmpEndDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(), 23, 59);
        if (findDate != null) {
            stDate = LocalDateTime.of(stYear, stMonth, findDate, 0, 0);
            endDate = LocalDateTime.of(stYear, stMonth, findDate, 23, 59);
        }
        return stYear != null ? group.startDateTime.between(stDate, endDate) : null;
    }

    private BooleanExpression statusNotDone(String status) {
        return "Y".equals(status) ? group.status.ne(GroupStatus.DONE) : null;
    }

    private BooleanExpression makeOnly(String makeOnly, Long memberId) {
        return "Y".equals(makeOnly) ? group.memberId.eq(memberId) : null;
    }

    private BooleanExpression joinOnly(String joinOnly, Long memberId) {
        return "Y".equals(joinOnly) ?
                group.id.in(JPAExpressions.select(groupAttend.group.id)
                        .from(groupAttend)
                        .where(groupAttend.member.id.eq(memberId))) : null;
    }

    private BooleanExpression searchAll(String keyword) {
        return StringUtils.isBlank(keyword) ? null :
                group.name.contains(keyword)
                        .or(group.categories.contains(JPAExpressions.select(category)
                                .from(category)
                                .where(category.name.contains(keyword))))
                ;
    }

}
