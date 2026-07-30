package com.example.token.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SignInResponse(
        boolean isLoggedIn,
        String url,
        String username,
        String userId,
        String accessToken,
        String refreshToken,
        String message
) {
}
