package com.example.oauth2_basic_board.dto;

public record ErrorResponse(
        int status,
        String message
) {
}
