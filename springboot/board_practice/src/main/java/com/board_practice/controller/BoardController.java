package com.board_practice.controller;

import com.board_practice.constant.SessionConst;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    @GetMapping("/")
    public String boardList(
            HttpSession session,
            Model model
    ) {
        setSession(session, model);
        return "board-list";
    }

    @GetMapping("/write")
    public String writeBoard(
            HttpSession session,
            Model model
    ) {
        setSession(session, model);
        return "board-write";
    }

    @GetMapping("/detail")
    public String boardDetail(
            @RequestParam Long id,
            HttpSession session,
            Model model
    ) {
        setSession(session, model);
        model.addAttribute("boardId", id);
        return "board-detail";

    }
    @GetMapping("/update/{id}")
    public String update(
            @PathVariable("id") long id,
            HttpSession session,
            Model model
    ) {
        setSession(session, model);
        return "board-update";
    }

    private void setSession(HttpSession session, Model model) {
        model.addAttribute(
                SessionConst.USER_ID,
                session.getAttribute(SessionConst.USER_ID)
        );

        model.addAttribute(
                SessionConst.USER_NAME,
                session.getAttribute(SessionConst.USER_NAME)
        );
    }
}