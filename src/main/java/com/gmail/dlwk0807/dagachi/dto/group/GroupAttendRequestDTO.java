package com.gmail.dlwk0807.dagachi.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupAttendRequestDTO {
    @Schema(description = "모임관리번호", nullable = true, example = "1")
    private Long groupId;

}
