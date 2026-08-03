package com.example.oauth2.dto;

import lombok.Builder;

@Builder
public record SignUpResponse(
        String url
) {
}
