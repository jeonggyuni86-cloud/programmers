package com.example.token.dto;

public record ErrorResponseDto(
        int status,
        String message
) {
}
