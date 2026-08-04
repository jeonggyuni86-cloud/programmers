package com.example.oauth2_basic_board.config.oauth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo of(AuthProvider provider, Map<String, Object> attributes) {
        return switch(provider) {
            case KAKAO -> new KakaoUserInfo(attributes);
            case LOCAL -> throw new IllegalArgumentException("LOCAL은 OAuth2 제공자가 아닙니다.");
        };
    }
}
