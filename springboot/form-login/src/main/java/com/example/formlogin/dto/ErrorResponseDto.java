package com.example.formlogin.dto;

public record ErrorResponseDto (
        int status,
        String message
) {
}
