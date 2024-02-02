package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupDeleteRequestDTO extends AOPMemberIdDTO {
    @Schema(description = "모임관리번호", example = "1")
    private Long groupId;

}
