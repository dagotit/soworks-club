package com.gmail.dlwk0807.dagotit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPassword {
    private String email;
    private String updatePassword;
}
