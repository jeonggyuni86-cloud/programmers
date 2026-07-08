package com.basicboard.controller;

import com.basicboard.domain.entity.Board;
import com.basicboard.dto.BoardDetailResponseDto;
import com.basicboard.dto.BoardListResponseDto;
import com.basicboard.dto.BoardWriteRequestDto;
import com.basicboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    // ResponseEntity 는 HTTP 응답의 3가지를 직접 제어하게 해주는 래퍼다
    // [상태 코드] + [헤더] + [본문]
    // 그냥 Resource만 리턴하면 파일 내용은 내려가지만,
    // Content-Disposition : attachment 헤더를 붙일 방법이 없다.
    // -> 그러면 다운로드가 아니라 브라우저가 파일을 그냥 열어버리고, 저장 파일명도 지정할 수 없다

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {

        return null;
    }


}
