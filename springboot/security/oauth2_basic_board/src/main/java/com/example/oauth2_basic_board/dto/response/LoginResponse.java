package com.example.oauth2_basic_board.dto.response;

import com.example.basicboard_token.domain.entity.Role;

public record LoginResponse(
        boolean isLoggedIn,
        String url,
        String userId,
        String username,
        Role role,
        String accessToken,
        String refreshToken,
        String message
) {
    public static LoginResponse success(
            String userId,
            String username,
            Role role,
            String accessToken,
            String refreshToken
    ) {
        return new LoginResponse(
                true,
                "/",
                userId,
                username,
                role,
                accessToken,
                refreshToken,
                "로그인에 성공했습니다"
        );
    }
}
