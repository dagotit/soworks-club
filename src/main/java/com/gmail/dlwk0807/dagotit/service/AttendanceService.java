package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.core.exception.AttendDuplicationException;
import com.gmail.dlwk0807.dagotit.entity.Attendance;
import com.gmail.dlwk0807.dagotit.entity.Member;
import com.gmail.dlwk0807.dagotit.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MemberService memberService;

    public void attend() {

        LocalDate now = LocalDate.now();
        if (attendanceRepository.existsByAttendDate(now)) {
            throw new AttendDuplicationException();
        }
        Member member = memberService.getCurrentMember();
        Attendance attendance = Attendance.builder()
                .attendDate(now)
                .attendance(true)
                .member(member)
                .build();

        member.addAttendance(attendance);
        attendanceRepository.save(attendance);
    }
}
