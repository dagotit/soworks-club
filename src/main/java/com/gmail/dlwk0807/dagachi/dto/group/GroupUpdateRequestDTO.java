package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import com.gmail.dlwk0807.dagachi.entity.Group;
import com.gmail.dlwk0807.dagachi.entity.GroupStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class GroupUpdateRequestDTO extends AOPMemberIdDTO {
    @Schema(description = "모임관리번호", nullable = true, example = "1")
    private Long groupId;
    @Schema(description = "카테고리관리번호", nullable = true, example = "")
    private List<Long> categoryIds;
    @Schema(description = "모임명", nullable = true, example = "이것은 모임명 수정")
    private String name;
    @Schema(description = "모임설명", nullable = false, example = "모임 설명란 수정")
    private String description;
    @Schema(description = "시작일시", nullable = true, example = "202401191900")
    @NotBlank(message = "시작일시는 필수입니다.")
    private String strStartDateTime;
    @Schema(description = "종료일시", nullable = true, example = "202401192200")
    @NotBlank(message = "종료일시는 필수입니다.")
    private String strEndDateTime;
    @Schema(description = "최대인원", nullable = false, example = "7")
    private Long groupMaxNum;

}
