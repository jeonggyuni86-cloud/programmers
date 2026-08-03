package com.example.oauth2.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
