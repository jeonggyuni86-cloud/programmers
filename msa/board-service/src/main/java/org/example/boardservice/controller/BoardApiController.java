package org.example.boardservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.dto.BoardPageResponseDto;
import org.example.boardservice.dto.BoardDetailResponseDto;
import org.example.boardservice.dto.BoardSearchRequestDto;
import org.example.boardservice.service.BoardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private static final int MAX_PAGE_SIZE = 100;

    private final BoardService boardService;

    @GetMapping("/search")
    public BoardPageResponseDto searchBoards(
            @ModelAttribute BoardSearchRequestDto condition,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.clamp(size, 1, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage - 1, safeSize);

        return boardService.searchBoards(condition, pageable);
    }

    @GetMapping("/{boardId}/with-comments")
    public BoardDetailResponseDto getBoardWithComments(@PathVariable Long boardId) {
        return boardService.getBoardWithComments(boardId);
    }
}
