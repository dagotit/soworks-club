package com.gmail.dlwk0807.dagachi.dto.calendar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CalendarRequestDTO {
    @Schema(description = "시작년도", nullable = true, example = "2024")
    private int stYear;
    @Schema(description = "종료년도", nullable = true, example = "2024")
    private int endYear;
    @Schema(description = "시작월", nullable = true, example = "1")
    private int stMonth;
    @Schema(description = "종료월", nullable = true, example = "2")
    private int endMonth;
    @Schema(description = "참여모임여부", nullable = false, example = "Y")
    private String joinOnly;
    @Schema(description = "내가만든모임여부", nullable = false, example = "N")
    private String makeOnly;
    @Schema(description = "모집중인 모임", nullable = false, example = "Y")
    private String statusNotDone;
}
