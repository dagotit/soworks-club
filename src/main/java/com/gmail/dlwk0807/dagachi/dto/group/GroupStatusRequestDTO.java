package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupStatusRequestDTO extends AOPMemberIdDTO {
    @Schema(description = "모임관리번호", nullable = true, example = "1")
    private Long groupId;
    @Schema(description = "모임상태 [WAITING, FULL, DONE, FAIL]", nullable = true, example = "DONE")
    private String status;
}
