package com.example.oauth2_basic_board.config.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    Map<String, Object> attributes();
    String id();
    String email();
    String name();
    String imageUrl();
}
