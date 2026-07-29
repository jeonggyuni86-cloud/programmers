package com.example.token.controller;

import com.example.token.dto.SignUpRequest;
import com.example.token.dto.SignUpResponse;
import com.example.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponse join(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return SignUpResponse.builder()
                .url("/users/login")
                .build();
    }
}
