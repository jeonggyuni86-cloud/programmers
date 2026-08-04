package com.example.oauth2_basic_board.dto.request;

public record CommentWriteRequest(
        String userId,
        String content
) {
}
