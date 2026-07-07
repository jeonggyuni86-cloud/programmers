package com.basicboard.controller;


import com.basicboard.constant.SessionConst;
import com.basicboard.dto.LoginRequestDto;
import com.basicboard.dto.LoginResponseDto;
import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.dto.MemberJoinResponseDto;
import com.basicboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
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
        //나중에 yaml 파일로 글로벌하게 관리한다
    }

    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto,
            HttpSession session
    ) {
        return memberService.login(dto)
                .map(
                        member -> {
                            session.setAttribute(SessionConst.USER_ID, member.getUserId());
                            session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                            return LoginResponseDto.success();
                        }
                ).orElseGet(LoginResponseDto::fail);
    }
}
