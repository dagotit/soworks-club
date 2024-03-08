package com.gmail.dlwk0807.dagachi.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateDTO {

    @Schema(description = "회사주소", nullable = false, example = "수정주소")
    private String address;
    @Schema(description = "회사상세주소", nullable = false, example = "수정 상세주소")
    private String addressDtl;
    @Schema(description = "사업자번호", nullable = false, example = "7777-777-7777")
    private String bizNo;
    @Schema(description = "회사명", nullable = false, example = "수정 소웍")
    private String companyName;
    @Schema(description = "창립일", nullable = false, example = "20191111")
    @Pattern(regexp = "\\d{8}", message = "창립일은 8자리 숫자입니다.")
    private String companyDate;
    @Schema(description = "이름", nullable = false, example = "홍수정")
    private String name;
    @Schema(description = "별명", nullable = false, example = "을지로불주먹")
    @Size(max = 12)
    private String nickname;
    @Schema(description = "생년월일", nullable = false, example = "20040909")
    @Pattern(regexp = "\\d{8}", message = "생년월일은 8자리 숫자입니다.")
    private String birth;
    @Schema(description = "인증대기상태", nullable = false, example = "Y")
    private String status;

}
