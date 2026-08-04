package com.example.oauth2_basic_board.controller;

import com.example.basicboard_token.dto.request.LoginRequest;
import com.example.basicboard_token.dto.request.MemberJoinRequest;
import com.example.basicboard_token.dto.request.MemberRoleUpdateRequest;
import com.example.basicboard_token.dto.response.LoginResponse;
import com.example.basicboard_token.dto.response.MemberResponse;
import com.example.basicboard_token.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{memberId}/role")
    public ResponseEntity<MemberResponse> updateRole(
            @PathVariable long memberId,
            @RequestBody MemberRoleUpdateRequest request
    ) {
        return ResponseEntity.ok(memberService.updateRole(memberId, request));
    }
}
