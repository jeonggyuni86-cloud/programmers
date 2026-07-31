package com.example.basicboard_token.dto.response;

public record RefreshTokenResponse(
        boolean validated,
        String accessToken,
        String refreshToken
) {

    public static RefreshTokenResponse refreshed(
            String accessToken,
            String refreshToken
    ) {
        return new RefreshTokenResponse(
                true,
                accessToken,
                refreshToken
        );
    }

    public static RefreshTokenResponse invalid() {
        return new RefreshTokenResponse(false, null, null);
    }
}
