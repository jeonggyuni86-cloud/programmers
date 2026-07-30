package com.example.token.dto;

import lombok.Builder;

@Builder
public record LogoutResponse(
        String message,
        String url
) {
}
