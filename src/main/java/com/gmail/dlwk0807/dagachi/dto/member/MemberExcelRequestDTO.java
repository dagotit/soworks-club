package com.gmail.dlwk0807.dagachi.dto.member;

import com.gmail.dlwk0807.dagachi.entity.Authority;
import com.gmail.dlwk0807.dagachi.entity.Company;
import com.gmail.dlwk0807.dagachi.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberExcelRequestDTO {

    @Schema(description = "로그인 ID", nullable = true, example = "test@test.com")
    @Email
    private String email;
    @Schema(description = "이름", nullable = false, example = "홍길동")
    private String name;
    @Schema(description = "별명", nullable = false, example = "닉네임")
    @Size(max = 12)
    private String nickname;
    @Schema(description = "생년월일", nullable = false, example = "20001212")
    @Pattern(regexp = "\\d{8}", message = "생년월일은 8자리 숫자입니다.")
    private String birth;
    @Schema(description = "인증대기상태", nullable = false, example = "Y")
    private String status;
    @Schema(description = "권한", nullable = true, example = "ROLE_USER")
    private String authority;

    public Member toMember(PasswordEncoder passwordEncoder, Company company) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(birth))
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .status("N")
                .profileImage("https://storage.googleapis.com/dagachi-image-bucket/default/profile_default.png")
                .authority(Authority.ROLE_USER)
                .company(company)
                .build();
    }

}
