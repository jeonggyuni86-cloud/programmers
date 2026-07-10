package com.basicboard.controller;


import com.basicboard.constant.SessionConst;
import com.basicboard.dto.*;
import com.basicboard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 API", description = "회원 가입 / 로그인 / 로그아웃(세션 기반 / 스프링 시큐리티 미사용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "아이디/비밀번호/이름으로 새 회원을 등록한다. 성공 시 로그인 페이지 경로를 돌려준다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "가입 성공"),
        @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디",
                content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
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

    @Operation(summary = "로그인",
            description = "아이디/비밀번호로 로그인한다. 성공 시 세션에 사용자 정보를 저장하고 loggedIn=true 를, 실패 시 loggedIn=false 와 안내 메시지를 돌려준다.")
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
