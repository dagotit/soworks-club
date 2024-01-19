package com.gmail.dlwk0807.dagotit.repository.impl;

import com.gmail.dlwk0807.dagotit.entity.Attendance;
import com.gmail.dlwk0807.dagotit.repository.AttendanceCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gmail.dlwk0807.dagotit.entity.QAttendance.attendance;

@Repository
@RequiredArgsConstructor
public class AttendanceCustomRepositoryImpl implements AttendanceCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Attendance> findAllByMonthAndYear(int month, int year) {
        return query.selectFrom(attendance)
                .where(monthEq(month), yearEq(year))
                .fetch();
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null? attendance.attendDate.year().eq(year) : null;
    }

    private BooleanExpression monthEq(Integer month) {
        return month != null? attendance.attendDate.month().eq(month) : null;
    }
}
