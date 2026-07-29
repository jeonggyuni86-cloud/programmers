package com.example.token.dto;

import lombok.Builder;

@Builder
public record SignUpResponse(
        String url
) {
}
