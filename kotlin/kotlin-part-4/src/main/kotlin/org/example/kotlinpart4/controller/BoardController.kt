package org.example.kotlinpart4.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class BoardController {

    @GetMapping("/")
    fun boardList() = "board-list"

    @GetMapping("/write")
    fun write() = "board-write"

    @GetMapping("/detail")
    fun detail(
        @RequestParam("id") id: Long,
        model: Model
    ): String {
        model.addAttribute("id", id)
        return "board-detail"
    }

    @GetMapping("/update/{id}")
    fun update(@PathVariable("id") id: Long, model: Model): String {
        model.addAttribute("id", id)
        return "board-update"
    }

}