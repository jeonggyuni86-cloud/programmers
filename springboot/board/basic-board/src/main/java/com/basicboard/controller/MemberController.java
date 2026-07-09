package com.basicboard.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//화면 이동만 담당한다

@Controller
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/join")
    public String join() {
        return "sign-up";
    }

    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        // "sign-in" 뷰 대신 redirect 쓰는 이유
        // 상태를 바꾸는 요청(로그아웃) 뒤엔 리다이렉트 해서, 새로고침시 로그아웃이 재실행을 막고,
        // 주소창도 members/login으로 맞춘다
        return "redirect:/members/login";
    }
}
