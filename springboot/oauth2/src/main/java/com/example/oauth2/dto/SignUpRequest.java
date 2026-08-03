package com.example.oauth2.dto;

import com.example.oauth2.domain.entity.entitiy.Role;
import com.example.oauth2.domain.entity.entitiy.User;

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
