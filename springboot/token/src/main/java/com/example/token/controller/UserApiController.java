package com.example.token.controller;

import com.example.token.config.jwt.JwtProperties;
import com.example.token.dto.SignInRequest;
import com.example.token.dto.SignInResponse;
import com.example.token.dto.SignUpRequest;
import com.example.token.dto.SignUpResponse;
import com.example.token.service.UserService;
import com.example.token.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
}
