package com.gmail.dlwk0807.dagachi.dto.member;

import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthRequestDTO {

    @Schema(description = "로그인 ID", nullable = false, example = "test@test.com")
    private String email;
    @Schema(description = "패스워드", nullable = false, example = "1234")
    private String password;
    @Schema(description = "주소", nullable = false, example = "테스트주소")
    private String address;
    @Schema(description = "패스워드", nullable = false, example = "상세주소")
    private String addressDtl;
    @Schema(description = "사업자번호", nullable = false, example = "1234-123-1234")
    private String bizNo;
    @Schema(description = "이름", nullable = false, example = "홍길동")
    private String name;
    @Schema(description = "회사명", nullable = false, example = "소웍")
    private String companyName;
    @Schema(description = "창립일", nullable = false, example = "20201201")
    private String companyDate;
    @Schema(description = "별명", nullable = false, example = "닉네임")
    private String nickname;
    @Schema(description = "생년월일", nullable = false, example = "20001212")
    private String birth;
    @Schema(description = "인증대기상태", nullable = false, example = "Y")
    private String status;
    @Schema(description = "권한", nullable = false, example = "ROLE_ADMIN")
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
