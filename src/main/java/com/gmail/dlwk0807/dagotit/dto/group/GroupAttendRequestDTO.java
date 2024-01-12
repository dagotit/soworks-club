package com.gmail.dlwk0807.dagotit.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupAttendRequestDTO {
    @NotBlank
    private String groupId;
    @NotBlank
    private String memberId;

}
