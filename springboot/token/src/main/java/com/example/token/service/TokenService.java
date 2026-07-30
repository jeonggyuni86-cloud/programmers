package com.example.token.service;

import com.example.token.config.jwt.JwtProperties;
import com.example.token.config.jwt.TokenProvider;
import com.example.token.domain.entitiy.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

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
}
