package com.board_practice.controller;

import com.board_practice.constant.SessionConst;
import com.board_practice.dto.LoginRequestDto;
import com.board_practice.dto.LoginResponseDto;
import com.board_practice.dto.MemberJoinRequestDto;
import com.board_practice.dto.MemberJoinResponseDto;
import com.board_practice.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/join")
    public MemberJoinResponseDto join(
            @RequestBody MemberJoinRequestDto memberJoinRequestDto
    ) {
        memberService.join(memberJoinRequestDto);
        return new MemberJoinResponseDto("/members/login");
    }


    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpSession session
    ) {
        return memberService.login(loginRequestDto)
                .map(
                        member -> {
                            session.setAttribute(SessionConst.USER_ID, member.getUserId());
                            session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                            return LoginResponseDto.success("로그인 성공");
                        }
                ).orElseGet(() -> LoginResponseDto.fail("로그인 실패"));
    }
}
