package com.board_practice.controller;

import com.board_practice.domain.entity.Board;
import com.board_practice.dto.*;
import com.board_practice.service.BoardService;
import com.board_practice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;
    private final FileService fileService;

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


    @PostMapping
    public void saveBoard(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getFile());
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable("id") long id) {
        Board board = boardService.getBoardDetail(id);

        return  BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .filePath(board.getFilePath())
                .userId(board.getUserId())
                .created(board.getCreated())
                .build();
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);
        String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .body(resource);
    }

    @PutMapping("/{id}")
    public void updateBoard(
            @PathVariable("id") long id,
            @RequestBody BoardUpdateRequestDto dto
    ) {
        boardService.updateBoard(id, dto);

    }

    @DeleteMapping("/{id}")
    public void deleteBoard(
            @PathVariable long id,
            @RequestBody BoardDeleteRequestDto dto
    ) {
        boardService.deleteBoard(id, dto);
    }
}
