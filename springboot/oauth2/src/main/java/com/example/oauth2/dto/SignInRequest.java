package com.example.oauth2.dto;

public record SignInRequest(
        String userId,
        String password
) {
}
