package com.example.oauth2_basic_board.dto.request;

import java.time.LocalDate;

public record BoardSearchRequest(
        String title,
        String userId,
        LocalDate from,
        LocalDate to
) {
}
