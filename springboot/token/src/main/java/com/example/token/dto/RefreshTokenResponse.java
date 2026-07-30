package com.example.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenResponse {

    private boolean validated;
    private String accessToken;
    private String refreshToken;

}
