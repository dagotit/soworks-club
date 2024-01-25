package com.gmail.dlwk0807.dagachi.service;

import com.gmail.dlwk0807.dagachi.dto.calendar.CalendarRequestDTO;
import com.gmail.dlwk0807.dagachi.dto.calendar.CalendarResponseDTO;
import com.gmail.dlwk0807.dagachi.dto.group.GroupListRequestDTO;
import com.gmail.dlwk0807.dagachi.entity.Attendance;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.repository.AttendanceCustomRepository;
import com.gmail.dlwk0807.dagachi.repository.GroupCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gmail.dlwk0807.dagachi.util.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final GroupCustomRepository groupCustomRepository;
    private final AttendanceCustomRepository attendanceCustomRepository;

    public List<CalendarResponseDTO> list(CalendarRequestDTO request) {
        GroupListRequestDTO groupListRequestDTO = new GroupListRequestDTO(request.getStYear(), request.getEndYear(), request.getStMonth(), request.getEndMonth(), request.getJoinOnly(), request.getMakeOnly(), request.getStatusNotDone());
        List<Group> allGroups = groupCustomRepository.findAllByFilter(groupListRequestDTO, getCurrentMemberId());
        List<Attendance> allAttendances = attendanceCustomRepository.findAllByMonthAndYear(request.getStMonth(), request.getStYear());
        List<CalendarResponseDTO> calendars = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

        if (!allGroups.isEmpty()) {
            allGroups.forEach(g -> {
                LocalDate groupDate = g.getStartDateTime().toLocalDate();
                allAttendances.forEach(a -> {
                    LocalDate attendDate = a.getAttendDate();
                    if (groupDate.isBefore(attendDate)) {
                        calendars.add(CalendarResponseDTO.of(count.get(), g, null));
                        count.getAndIncrement();
                    } else if (groupDate.isEqual(attendDate)) {
                        calendars.add(CalendarResponseDTO.of(count.get(), g, attendDate));
                        count.getAndIncrement();
                    } else {
                        calendars.add(CalendarResponseDTO.of(count.get(), null, attendDate));
                        count.getAndIncrement();
                    }
                });
                if (allAttendances.isEmpty()) {
                    calendars.add(CalendarResponseDTO.of(count.get(), g, null));
                    count.getAndIncrement();
                }
            });
        }
        else {
            allAttendances.forEach(a -> {
                LocalDate attendDate = a.getAttendDate();
                calendars.add(CalendarResponseDTO.of(count.get(), null, attendDate));
                count.getAndIncrement();
            });
        }

        return calendars;
    }
}
