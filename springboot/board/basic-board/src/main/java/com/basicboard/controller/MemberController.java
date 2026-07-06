package com.basicboard.controller;

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

}
