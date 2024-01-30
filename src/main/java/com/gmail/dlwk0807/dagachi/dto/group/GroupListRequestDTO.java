package com.gmail.dlwk0807.dagachi.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupListRequestDTO {

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
    @Schema(description = "일", nullable = false, example = "12")
    private Integer findDate;
}
