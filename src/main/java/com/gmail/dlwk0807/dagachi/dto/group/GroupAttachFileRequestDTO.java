package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupAttachFileRequestDTO extends AOPMemberIdDTO {

    @Schema(description = "모입관리번호", nullable = true, example = "1")
    private Long groupId;

}
