package com.gmail.dlwk0807.dagachi.dto.group;

import com.gmail.dlwk0807.dagachi.core.aspect.AOPMemberIdDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupDeleteRequestDTO extends AOPMemberIdDTO {
    private Long groupId;
    private String category;
    private String name;
    private String description;
    @NotBlank(message = "시작일시는 필수입니다.")
    private String strStartDateTime;
    @NotBlank(message = "종료일시는 필수입니다.")
    private String strEndDateTime;
    private String groupImage;
    private Long groupMaxNum;

}
