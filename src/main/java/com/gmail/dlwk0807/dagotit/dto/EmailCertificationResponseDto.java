package com.gmail.dlwk0807.dagotit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailCertificationResponseDto {
    private String email;
    private String certificationNumber;
}