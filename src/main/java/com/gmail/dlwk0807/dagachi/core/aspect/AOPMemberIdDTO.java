package com.gmail.dlwk0807.dagachi.core.aspect;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class AOPMemberIdDTO {
    @Schema(description = "회원관리번호", nullable = true, example = "1")
    private Long memberId;
}
