package com.example.oauth2.dto;

import lombok.Builder;

@Builder
public record LogoutResponse(
        String message,
        String url
) {
}
