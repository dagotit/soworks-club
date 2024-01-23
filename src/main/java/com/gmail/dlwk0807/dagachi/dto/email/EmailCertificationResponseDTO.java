package com.gmail.dlwk0807.dagachi.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailCertificationResponseDTO {
    private String email;
    private String certificationNumber;
}