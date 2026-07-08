package com.basicboard.controller;

import com.basicboard.domain.entity.Board;
import com.basicboard.dto.BoardDetailResponseDto;
import com.basicboard.dto.BoardListResponseDto;
import com.basicboard.dto.BoardWriteRequestDto;
import com.basicboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping
    public BoardListResponseDto getBoardList(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        List<Board> boards = boardService.getBoards(page, size);
        int totalBoards = boardService.getTotalBoards();

        //전체 페이지 수 계산
        int totalPages = (int)Math.ceil((double) totalBoards/ size);

        // 마지막 페이지 여부
        boolean last = page >= totalPages;

        return BoardListResponseDto.builder()
                .last(last)
                .totalPages(totalPages)
                .boards(boards)
                .build();
    }

    @PostMapping
    public void saveBoard(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getFile());
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable("id") long id) {
        Board board = boardService.getBoardDetail(id);

        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .filePath(board.getFilePath())
                .userId(board.getUserId())
                .created(board.getCreated())
                .build();
    }


}
