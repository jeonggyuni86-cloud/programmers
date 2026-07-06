package com.basicboard.controller;


import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.dto.MemberJoinResponseDto;
import com.basicboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//화면 이동이 아닌 데이터 전송
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;
    @PostMapping("/join")
    public MemberJoinResponseDto join(
            @RequestBody MemberJoinRequestDto dto
            //ajax 내 key값
    ) {
        memberService.join(dto);
        return new MemberJoinResponseDto("/members/login");
        //ajax 에서 지금 준 /members/login 으로 리다이렉션 시킨다
    }
}
