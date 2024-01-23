package com.gmail.dlwk0807.dagachi.dto.calendar;

import com.gmail.dlwk0807.dagachi.entity.Group;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CalendarResponseDTO {
    private String id;
    private String groupId;
    private String title; // 모임 제목
    private Boolean allDay; // 종일
    private String start; // 모임 시작일 new Date('2023-12-13') 이렇게 했지만 YYYY-MM-DD만
    private String end; // 모임 종료일
    private String attendanceDate; // 출석체크한날
    private String colorEvento; // 모임 배경색
    private String color; // 모임 글자색

    public static CalendarResponseDTO of(int id, Group group, LocalDate localDate) {
        if (group == null) {
            return CalendarResponseDTO.builder()
                    .id(String.valueOf(id))
                    .attendanceDate(localDate == null ? null : localDate.toString())
                    .colorEvento("")
                    .color("")
                    .build();
        }

        return CalendarResponseDTO.builder()
                .id(String.valueOf(id))
                .groupId(String.valueOf(group.getId()))
                .title(group.getName())
                .start(group.getStartDateTime().toLocalDate().toString())
                .end(group.getEndDateTime().toLocalDate().toString())
                .attendanceDate(localDate == null ? null : localDate.toString())
                .colorEvento("")
                .color("")
                .build();
    }

}
