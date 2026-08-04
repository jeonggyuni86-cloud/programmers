package com.example.oauth2_basic_board.dto.request;

public record LoginRequest(
        String userId,
        String password
) {
}
