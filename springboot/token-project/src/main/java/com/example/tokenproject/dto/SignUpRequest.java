package com.example.tokenproject.dto;

import com.example.tokenproject.domain.entity.User;
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
