package com.board_practice.controller;

import com.board_practice.domain.entity.Board;
import com.board_practice.dto.BoardDetailResponseDto;
import com.board_practice.dto.BoardListResponseDto;
import com.board_practice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping
    public BoardListResponseDto getBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Board> boards = boardService.getBoards(page, size);
        int totalBoards = boardService.getTotalBoards();
        int totalPages = (int) Math.ceil((double) totalBoards / size);
        boolean last = page >= totalPages;

        return BoardListResponseDto.builder()
                .boards(boards)
                .totalPages(totalPages)
                .last(last)
                .build();
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoard(
            @PathVariable Long id
    ) {
        Board board = boardService.getBoardDetails(id);

        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .created(board.getCreated())
                .userId(board.getUserId())
                .filePath(board.getFilePath())
                .build();
    }
}
