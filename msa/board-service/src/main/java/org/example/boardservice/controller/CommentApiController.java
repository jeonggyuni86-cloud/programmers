package org.example.boardservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.dto.CommentWriteRequestDto;
import org.example.boardservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(
            @PathVariable Long boardId,
            @RequestBody CommentWriteRequestDto request
    ) {
        commentService.addComment(boardId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(boardId, commentId);
        return ResponseEntity.noContent().build();
    }
}
