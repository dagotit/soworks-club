package com.gmail.dlwk0807.dagotit.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupAttachFileRequestDTO {
    private Long groupId;
    private Long memberId;

}
