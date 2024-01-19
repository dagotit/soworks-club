package com.gmail.dlwk0807.dagotit.service;

import com.gmail.dlwk0807.dagotit.dto.calendar.CalendarRequestDTO;
import com.gmail.dlwk0807.dagotit.dto.calendar.CalendarResponseDTO;
import com.gmail.dlwk0807.dagotit.entity.Attendance;
import com.gmail.dlwk0807.dagotit.entity.Group;
import com.gmail.dlwk0807.dagotit.repository.AttendanceCustomRepository;
import com.gmail.dlwk0807.dagotit.repository.GroupCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    private final GroupCustomRepository groupCustomRepository;
    private final AttendanceCustomRepository attendanceCustomRepository;

    public List<CalendarResponseDTO> list(CalendarRequestDTO calendarRequestDTO) {

        List<Group> allGroups = groupCustomRepository.findAllByMonthAndYear(calendarRequestDTO.getMonth(), calendarRequestDTO.getYear());
        List<Attendance> allAttendances = attendanceCustomRepository.findAllByMonthAndYear(calendarRequestDTO.getMonth(), calendarRequestDTO.getYear());
        List<CalendarResponseDTO> calendars = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

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
        });

        return calendars;
    }
}
