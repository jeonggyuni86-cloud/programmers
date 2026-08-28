package org.example.webservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.webservice.dto.SignInRequestDto;
import org.example.webservice.dto.SignInResponseDto;
import org.example.webservice.dto.SignUpRequestDto;
import org.example.webservice.dto.SignUpResponseDto;
import org.example.webservice.service.AuthService;
import org.example.webservice.util.HeaderRelayUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.signUp(signUpRequestDto);
    }
    @PostMapping("/login")
    public SignInResponseDto login(
            @RequestBody SignInRequestDto signInRequestDto,
            HttpServletResponse response
    ) {
        return HeaderRelayUtil.relaySetCookie(authService.signIn(signInRequestDto), response);
    }


}
