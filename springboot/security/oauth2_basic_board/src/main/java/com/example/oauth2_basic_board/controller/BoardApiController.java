package com.example.oauth2_basic_board.controller;

import com.example.oauth2_basic_board.dto.request.BoardSearchRequest;
import com.example.oauth2_basic_board.dto.request.BoardUpdateRequest;
import com.example.oauth2_basic_board.dto.request.BoardWriteRequest;
import com.example.oauth2_basic_board.dto.response.BoardAuthorStatResponse;
import com.example.oauth2_basic_board.dto.response.BoardDetailResponse;
import com.example.oauth2_basic_board.dto.response.BoardListItemResponse;
import com.example.oauth2_basic_board.dto.response.BoardListResponse;
import com.example.oauth2_basic_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<BoardListResponse> getBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                boardService.getBoards(page, size)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveBoard(
            @ModelAttribute BoardWriteRequest request
    ) {
        boardService.saveBoard(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName
    ) {
        Resource resource = boardService.downloadFile(fileName);

        String encodedFileName =
                URLEncoder.encode(
                        resource.getFilename(),
                        StandardCharsets.UTF_8
                ).replace("+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFileName
                )
                .body(resource);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BoardListItemResponse>> searchBoards(
            @ModelAttribute BoardSearchRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable =
                PageRequest.of(page - 1, size);

        return ResponseEntity.ok(
                boardService.searchBoards(request, pageable)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats/authors")
    public ResponseEntity<List<BoardAuthorStatResponse>> getAuthors(
            @RequestParam(defaultValue = "1") long minCount
    ) {
        return ResponseEntity.ok(
                boardService.getAuthorsStats(minCount)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> getBoardDetail(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(
                boardService.getBoardDetail(id)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> updateBoard(
            @PathVariable long id,
            @ModelAttribute BoardUpdateRequest request
    ) {
        boardService.updateBoard(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable long id
    ) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }
}