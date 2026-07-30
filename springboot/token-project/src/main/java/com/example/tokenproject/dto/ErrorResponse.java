package com.example.tokenproject.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
