package com.example.token.dto;

public record SignInRequest(
        String userId,
        String password
) {
}
