package com.example.basicboard_token.dto.response;

public record LoginResponse(
        boolean isLoggedIn,
        String url,
        String userId,
        String username,
        String accessToken,
        String refreshToken,
        String message
) {
    public static LoginResponse success(
            String userId,
            String username,
            String accessToken,
            String refreshToken
    ) {
        return new LoginResponse(
                true,
                "/",
                userId,
                username,
                accessToken,
                refreshToken,
                "로그인에 성공했습니다"
        );
    }
    public static LoginResponse fail(
            String userId,
            String username,
            String accessToken,
            String refreshToken
    ) {
        return new LoginResponse(
                true,
                "/",
                userId,
                username,
                accessToken,
                refreshToken,
                "로그인에 성공했습니다"
        );
    }
}
