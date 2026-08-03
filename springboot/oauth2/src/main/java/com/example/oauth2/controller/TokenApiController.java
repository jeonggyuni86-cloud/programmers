package com.example.oauth2.controller;

import com.example.oauth2.config.jwt.jwt.JwtProperties;
import com.example.oauth2.dto.ErrorResponse;
import com.example.oauth2.dto.RefreshTokenResponse;
import com.example.oauth2.service.TokenService;
import com.example.oauth2.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenApiController {

    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        RefreshTokenResponse refreshTokenResponse = tokenService.refreshToken(request.getCookies());

        if(refreshTokenResponse.isValidated()) {

            CookieUtil.addCookie(
                    response,
                    CookieUtil.REFRESH_TOKEN_COOKIE,
                    refreshTokenResponse.getRefreshToken(),
                    (int)jwtProperties.getRefreshTokenValidity().toSeconds()
            );
            return ResponseEntity.ok(refreshTokenResponse);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "리프레시 토큰이 만료되었습니다"
                        )
                );

    }
}
