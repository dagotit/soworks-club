package com.gmail.dlwk0807.dagotit.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateDTO {

    private String email;
    private String address;
    private String addressDtl;
    private String bizNo;
    private String name;
    private String nickname;
    private String birth;
    private String status;
    private String picture;
    private String companyName;
    private String companyDate;
    private String authority;

}
