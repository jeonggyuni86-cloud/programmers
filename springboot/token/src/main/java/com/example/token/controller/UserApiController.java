package com.example.token.controller;

import com.example.token.config.jwt.JwtProperties;
import com.example.token.config.security.CustomUserDetails;
import com.example.token.domain.entitiy.User;
import com.example.token.dto.*;
import com.example.token.service.UserService;
import com.example.token.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/join")
    public SignUpResponse join(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return SignUpResponse.builder()
                .url("/users/login")
                .build();
    }

    @PostMapping("/login")
    public SignInResponse login(
            @RequestBody SignInRequest signInRequest,
            HttpServletResponse response
    ) {
        var signInResponse = userService.login(signInRequest);

        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                signInResponse.refreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );
        return null;
    }

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        return UserInfoResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .role(user.getRole())
                .build();

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public AuthorityResponse authority() {
        return AuthorityResponse.builder()
                .message("일반 사용자만 볼 수 있는 권한입니다")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public AuthorityResponse authorityAdmin() {
        return AuthorityResponse.builder()
                .message("관리자만 볼 수 있는 권한입니다")
                .build();
    }
}
