package com.example.formlogin.dto;

import lombok.Builder;

@Builder
public record SignInResponseDto(
        boolean isLoggedIn,
        String url,
        String userName,
        String userId,
        String message
) {
}
