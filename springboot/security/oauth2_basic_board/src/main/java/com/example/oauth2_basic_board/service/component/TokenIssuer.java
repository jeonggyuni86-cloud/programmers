package com.example.oauth2_basic_board.service.component;

import com.example.oauth2_basic_board.config.jwt.JwtProperties;
import com.example.oauth2_basic_board.config.jwt.TokenProvider;
import com.example.oauth2_basic_board.config.jwt.TokenStatus;
import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.dto.response.RefreshTokenResponse;
import com.example.oauth2_basic_board.util.CookieUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenIssuer {
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TokenPair(
            String accessToken,
            String refreshToken
    ){}

    public TokenPair issueToken(Member user) {
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
            Member member = tokenProvider.getTokenDetails(refreshToken);
            TokenPair tokenPair = issueToken(member);

            return RefreshTokenResponse.refreshed(
                    tokenPair.accessToken(),
                    tokenPair.refreshToken()
            );
        }
        return RefreshTokenResponse.invalid();
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
