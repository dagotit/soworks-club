package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.entity.Attendance;
import com.gmail.dlwk0807.dagachi.entity.Member;
import com.gmail.dlwk0807.dagachi.repository.AttendanceRepository;
import com.gmail.dlwk0807.dagachi.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AuthUtil authUtil;

    public String attend() {

        LocalDate now = LocalDate.now();
        if (attendanceRepository.existsByAttendDate(now)) {
            return "이미 출석체크 하셨습니다.";
        }
        Member member = authUtil.getCurrentMember();
        Attendance attendance = Attendance.builder()
                .attendDate(now)
                .member(member)
                .build();

        member.addAttendance(attendance);
        attendanceRepository.save(attendance);
        return "출석완료";
    }
}