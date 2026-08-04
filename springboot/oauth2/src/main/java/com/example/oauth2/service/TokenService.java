package com.example.oauth2.service;

import com.example.oauth2.config.jwt.jwt.JwtProperties;
import com.example.oauth2.config.jwt.jwt.TokenProvider;
import com.example.oauth2.config.jwt.jwt.TokenStatus;
import com.example.oauth2.domain.entity.entitiy.User;
import com.example.oauth2.dto.RefreshTokenResponse;
import com.example.oauth2.dto.SignupPayload;
import com.example.oauth2.util.CookieUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TokenPair(
            String accessToken,
            String refreshToken
    ) {}

    public TokenPair issueToken(User user) {
        String accessToken = tokenProvider.generateToken(
                user,
                jwtProperties.getAccessTokenValidity()
        );

        String refreshToken = tokenProvider.generateToken(
                user,
                jwtProperties.getRefreshTokenValidity()
        );
        return new TokenPair(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(Cookie[] cookies) {
        String refreshToken = getRefreshToken(cookies);

        if(refreshToken != null && tokenProvider.validateToken(refreshToken) == TokenStatus.VALID) {
            User user = tokenProvider.getTokenDetails(refreshToken);
            TokenPair tokenPair = issueToken(user);

            return RefreshTokenResponse.builder()
                    .validated(true)
                    .accessToken(tokenPair.accessToken())
                    .refreshToken(tokenPair.refreshToken())
                    .build();
        }

        return RefreshTokenResponse.builder()
                .validated(false)
                .build();
    }

    public SignupPayload getSignupPayload(String token) {
        return tokenProvider.getSignupPayload(token);
    }

    private String getRefreshToken(Cookie[] cookies) {
        if(cookies == null) return null;

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(CookieUtil.REFRESH_TOKEN_COOKIE)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
