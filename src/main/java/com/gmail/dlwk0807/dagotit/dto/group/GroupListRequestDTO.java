package com.gmail.dlwk0807.dagotit.dto.group;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupListRequestDTO {
    private int stYear;
    private int endYear;
    private int stMonth;
    private int endMonth;
    private String joinOnly;
    private String makeOnly;
    private String statusNotDone;
}
