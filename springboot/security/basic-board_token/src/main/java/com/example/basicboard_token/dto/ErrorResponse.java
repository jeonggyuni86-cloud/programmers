package com.example.basicboard_token.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
