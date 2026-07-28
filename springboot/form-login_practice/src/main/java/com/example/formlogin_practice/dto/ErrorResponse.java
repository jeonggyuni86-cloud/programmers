package com.example.formlogin_practice.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
