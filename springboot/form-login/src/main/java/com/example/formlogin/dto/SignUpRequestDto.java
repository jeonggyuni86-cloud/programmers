package com.example.formlogin.dto;

import com.example.formlogin.domain.entity.User;

public record SignUpRequestDto (
        String userId,
        String password,
        String userName
) {

    public User toUser(String encodePassword) {
        return User.builder()
                .userId(userId)
                .password(encodePassword)
                .name(userName)
                .build();
    }
}
