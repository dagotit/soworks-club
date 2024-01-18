package com.gmail.dlwk0807.dagotit.dto.member;

import com.gmail.dlwk0807.dagotit.core.aspect.AOPMemberIdDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateDTO extends AOPMemberIdDTO {

    private String email;
    private String address;
    private String addressDtl;
    private String bizNo;
    private String name;
    private String nickname;
    private String birth;
    private String status;
    private String profileImage;
    private String companyName;
    private String companyDate;
    private String authority;

}
