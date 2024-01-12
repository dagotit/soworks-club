package com.gmail.dlwk0807.dagotit.dto.token;

import lombok.*;

@Getter
@Builder
public class TokenDTO {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;
    private final String memberId;
}
