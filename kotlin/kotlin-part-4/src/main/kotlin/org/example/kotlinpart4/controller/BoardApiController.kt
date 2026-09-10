package org.example.kotlinpart4.controller

import org.example.kotlinpart4.dto.BoardCreateRequest
import org.example.kotlinpart4.dto.BoardPageResponse
import org.example.kotlinpart4.service.BoardService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/boards")
class BoardApiController(private val boardService: BoardService) {

    @GetMapping
    fun getBoards(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "keyword", required = false) keyword: String?
    ) = BoardPageResponse.from(boardService.getBoards(page, size, keyword))

    @PostMapping
     fun createBoard(
         @RequestBody request: BoardCreateRequest
    ) = boardService.createBoard(request)
}