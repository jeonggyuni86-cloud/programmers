package com.example.basicboard_token.controller;

import com.example.basicboard_token.dto.request.LoginRequest;
import com.example.basicboard_token.dto.request.MemberJoinRequest;
import com.example.basicboard_token.dto.response.LoginResponse;
import com.example.basicboard_token.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(
            @RequestBody MemberJoinRequest memberJoinRequest
    ) {
        memberService.join(memberJoinRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(
                memberService.login(loginRequest)
        );
    }
}