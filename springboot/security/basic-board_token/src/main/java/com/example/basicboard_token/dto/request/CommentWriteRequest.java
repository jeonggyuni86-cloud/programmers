package com.example.basicboard_token.dto.request;

public record CommentWriteRequest(
        String userId,
        String content
) {
}
