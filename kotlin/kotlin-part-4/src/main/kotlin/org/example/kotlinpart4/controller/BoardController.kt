package org.example.kotlinpart4.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BoardController {

    @GetMapping("/")
    fun boardList() = "board-list"

}