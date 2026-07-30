package com.example.tokenproject.dto;

import lombok.Builder;

@Builder
public record SignInResponse (
        boolean isLoggedIn,
        String url,
        String userName,
        String userId,
        String message
) {
}
