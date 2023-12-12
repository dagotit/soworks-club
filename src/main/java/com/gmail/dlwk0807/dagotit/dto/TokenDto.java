package com.gmail.dlwk0807.dagotit.dto;

import lombok.*;

@Getter
@Builder
public class TokenDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;
}
