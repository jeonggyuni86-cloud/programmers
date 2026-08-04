package com.example.oauth2_basic_board.controller;

import com.example.basicboard_token.dto.request.CommentWriteRequest;
import com.example.basicboard_token.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(
            @PathVariable long boardId,
            @RequestBody CommentWriteRequest request
    ) {
        commentService.addComment(boardId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable long boardId,
            @PathVariable long commentId
    ) {
        commentService.deleteComment(boardId, commentId);
        return ResponseEntity.noContent().build();
    }
}
