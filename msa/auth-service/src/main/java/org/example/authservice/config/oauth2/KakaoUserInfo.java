package org.example.authservice.config.oauth2;

import java.util.Map;

public record KakaoUserInfo(
        Map<String, Object> attributes
) implements OAuth2UserInfo {
    @Override
    public String id() {
        Object id = attributes.get("id");
        return id == null ? null : String.valueOf(id);
    }

    @Override
    public String email() {
        Map<String, Object> kakaoAccount = kakaoAccount();
        return kakaoAccount == null ? null : String.valueOf(kakaoAccount.get("email"));
    }

    @Override
    public String name() {
        Map<String, Object> stringObjectMap = kakaoProfile();
        return stringObjectMap == null ? null : String.valueOf(stringObjectMap.get("name"));
    }

    @Override
    public String imageUrl() {
        Map<String, Object> stringObjectMap = kakaoProfile();
        return stringObjectMap == null ? null : String.valueOf(stringObjectMap.get("image_url"));
    }


    private Map<String, Object> kakaoAccount() {
        return (Map<String, Object>)attributes.get("Kakao account");
    }

    private Map<String, Object> kakaoProfile() {
        return (Map<String, Object>)kakaoAccount().get("profile");
    }
}
