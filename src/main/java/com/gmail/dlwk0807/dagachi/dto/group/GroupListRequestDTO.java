package com.gmail.dlwk0807.dagachi.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupListRequestDTO {
    private int stYear;
    private int endYear;
    private int stMonth;
    private int endMonth;
    private String joinOnly;
    private String makeOnly;
    private String statusNotDone;
}
