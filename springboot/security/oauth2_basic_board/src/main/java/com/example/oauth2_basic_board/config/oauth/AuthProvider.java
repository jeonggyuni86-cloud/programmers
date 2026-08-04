package com.example.oauth2_basic_board.config.oauth;

public enum AuthProvider {
    LOCAL,
    KAKAO;

    public static AuthProvider from(String registrationId) {
        return AuthProvider.valueOf(registrationId.toUpperCase());
    }
}
