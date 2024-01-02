package com.gmail.dlwk0807.dagotit.dto;

import com.gmail.dlwk0807.dagotit.entity.Authority;
import com.gmail.dlwk0807.dagotit.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;
    private String address;
    private String bizNo;
    private String name;
    private String companyName;
    private String companyDate;
    private String nickname;
    private String birth;
    private String status;
    private String picture;
    private String authority;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .address(address)
                .bizNo(bizNo)
                .name(name)
                .companyName(companyName)
                .companyDate(companyDate)
                .nickname(nickname)
                .birth(birth)
                .status(status)
//                .picture(picture) 사진 저장 보류
                .authority(Authority.valueOf(authority))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
