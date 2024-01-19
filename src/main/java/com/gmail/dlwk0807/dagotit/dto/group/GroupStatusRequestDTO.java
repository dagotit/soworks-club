package com.gmail.dlwk0807.dagotit.dto.group;

import com.gmail.dlwk0807.dagotit.core.aspect.AOPMemberIdDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupStatusRequestDTO extends AOPMemberIdDTO {
    private Long groupId;
    private String status;
}
