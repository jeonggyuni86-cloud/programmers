package com.example.basicboard_token.dto.request;

public record LoginRequest(
        String userId,
        String password
) {
}
