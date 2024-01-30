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
import java.util.HashMap;
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
        GroupListRequestDTO groupListRequestDTO = new GroupListRequestDTO(request.getStYear(), request.getEndYear(), request.getStMonth(), request.getEndMonth(), request.getJoinOnly(), request.getMakeOnly(), request.getStatusNotDone(), null);
        List<Group> allGroups = groupCustomRepository.findAllByFilter(groupListRequestDTO, getCurrentMemberId());
        List<Attendance> allAttendances = attendanceCustomRepository.findAllByMonthAndYear(request.getStMonth(), request.getStYear());
        HashMap<LocalDate, CalendarResponseDTO> calendarMap = new HashMap<>();
        int count = 0;

        for (Group group : allGroups) {
            LocalDate groupDate = group.getStartDateTime().toLocalDate();
            calendarMap.put(groupDate, CalendarResponseDTO.of(++count, group, null));
        }

        for (Attendance attendance : allAttendances) {
            LocalDate attendDate = attendance.getAttendDate();
            CalendarResponseDTO calendarResponseDTO = calendarMap.get(attendDate);

            if (calendarResponseDTO != null) {
                calendarResponseDTO.updateAttendanceDate(attendDate.toString());
            } else {
                calendarMap.put(attendDate, CalendarResponseDTO.of(++count, null, attendDate));
            }
        }

        return new ArrayList<>(calendarMap.values());
    }
}
