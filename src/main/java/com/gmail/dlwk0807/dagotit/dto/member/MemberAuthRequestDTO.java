package com.gmail.dlwk0807.dagotit.dto.member;

import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthRequestDTO {

    private String email;
    private String password;
    private String address;
    private String addressDtl;
    private String bizNo;
    private String name;
    private String companyName;
    private String companyDate;
    private String nickname;
    private String birth;
    private String status;
    private String authority;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .address(address)
                .addressDtl(addressDtl)
                .bizNo(bizNo)
                .name(name)
                .companyName(companyName)
                .companyDate(companyDate)
                .nickname(nickname)
                .birth(birth)
                .status(status)
                .profileImage("https://storage.googleapis.com/dagachi-image-bucket/default/profile_default.png")
                .authority(Authority.valueOf(authority))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
