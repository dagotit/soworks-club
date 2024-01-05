package com.gmail.dlwk0807.dagotit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupAttendRequestDto {
    @NotBlank
    private String groupId;
    @NotBlank
    private String memberId;

}
