package com.example.oauth2.dto;

import com.example.oauth2.domain.entity.entitiy.Role;

public record OAuthSignUpRequest (
        String signupToken,
        Role role
) {
}
