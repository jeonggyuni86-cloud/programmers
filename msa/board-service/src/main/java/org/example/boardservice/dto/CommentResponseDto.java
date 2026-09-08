package org.example.boardservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.boardservice.domain.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        String userId,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime created
) {

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreated()
        );
    }
}
