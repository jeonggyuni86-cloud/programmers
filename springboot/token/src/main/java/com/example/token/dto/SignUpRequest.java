package com.example.token.dto;

import com.example.token.domain.entitiy.Role;
import com.example.token.domain.entitiy.User;

public record SignUpRequest(
        String userId,
        String password,
        String userName,
        Role role
) {
    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role != null ? role : Role.ROLE_USER)
                .build();
    }

}
