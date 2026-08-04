package com.example.oauth2_basic_board.config.oauth;

import java.util.Map;

public record KakaoUserInfo (
        Map<String, Object> attributes
) implements OAuth2UserInfo {
    @Override
    public String id() {
        Object id = attributes.get("id");   // JSON 숫자(Long)로 온다
        return id == null ? null : String.valueOf(id);
    }

    @Override
    public String email() {
        Map<String, Object> account = kakaoAccount();
        return account == null ? null : (String) account.get("email");
    }

    @Override
    public String name() {
        Map<String, Object> profile = profile();
        return profile == null ? null : (String) profile.get("nickname");
    }

    @Override
    public String imageUrl() {
        Map<String, Object> profile = profile();
        return profile == null ? null : (String) profile.get("profile_image_url");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> kakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> profile() {
        Map<String, Object> account = kakaoAccount();
        return account == null ? null : (Map<String, Object>) account.get("profile");
    }
}
