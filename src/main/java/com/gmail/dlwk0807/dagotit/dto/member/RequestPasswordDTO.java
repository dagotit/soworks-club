package com.gmail.dlwk0807.dagotit.dto.member;

import com.gmail.dlwk0807.dagotit.core.aspect.AOPMemberIdDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPasswordDTO extends AOPMemberIdDTO {
    private String updatePassword;
}
