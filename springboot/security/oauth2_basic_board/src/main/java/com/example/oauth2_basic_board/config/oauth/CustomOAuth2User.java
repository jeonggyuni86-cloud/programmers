package com.example.oauth2_basic_board.config.oauth;

import com.example.oauth2_basic_board.domain.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final Member member;
    private final AuthProvider provider;
    private final OAuth2UserInfo userInfo;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    public static CustomOAuth2User unregistered(
            AuthProvider provider,
            OAuth2UserInfo userInfo,
            Map<String, Object> attributes,
            String nameAttributeKey
    ) {
        return new CustomOAuth2User(null, provider, userInfo, attributes, nameAttributeKey);
    }

    public boolean isRegistered() {
        return member != null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (member == null) {
            return List.of(new SimpleGrantedAuthority("ROLE_GUEST")); // 미가입 임시 권한
        }
        return List.of(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get(nameAttributeKey));
    }
}
