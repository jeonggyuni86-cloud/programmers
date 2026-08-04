package com.example.oauth2.dto;

import com.example.oauth2.config.oauth2.AuthProvider;

public record SignupPayload(
        AuthProvider provider,
        String providerId, // SNS 회원번호 (토큰의 sub 클레임에서 복원)
        String email,
        String name
) {
}
