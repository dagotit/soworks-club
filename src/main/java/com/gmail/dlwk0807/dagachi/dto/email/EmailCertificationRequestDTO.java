package com.gmail.dlwk0807.dagachi.dto.email;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class EmailCertificationRequestDTO {

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    @Schema(description = "이메일", nullable = true, example = "test@test.com")
    private String email;
    @Schema(description = "비밀번호", nullable = true, example = "1234")
    private String name;
}