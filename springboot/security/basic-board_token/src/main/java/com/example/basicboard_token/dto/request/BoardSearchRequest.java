package com.example.basicboard_token.dto.request;

import java.time.LocalDate;

public record BoardSearchRequest(
        String title,
        String userId,
        LocalDate from,
        LocalDate to
) {
}
