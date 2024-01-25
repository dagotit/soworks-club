package com.gmail.dlwk0807.dagachi.dto.calendar;

import lombok.Data;

@Data
public class CalendarRequestDTO {
    private int stYear;
    private int endYear;
    private int stMonth;
    private int endMonth;
    private String joinOnly;
    private String makeOnly;
    private String statusNotDone;
}
