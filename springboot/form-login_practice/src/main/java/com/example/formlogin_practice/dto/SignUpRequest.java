package com.example.formlogin_practice.dto;

import com.example.formlogin_practice.domain.entity.User;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String userId,
        String password,
        String userName
) {
    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .build();
    }
}
