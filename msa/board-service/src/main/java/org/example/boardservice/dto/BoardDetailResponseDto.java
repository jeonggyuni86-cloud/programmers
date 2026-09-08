package org.example.boardservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.boardservice.domain.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

public record BoardDetailResponseDto(
        Long id,
        String title,
        String content,
        List<CommentResponseDto> comments,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime created,
        String userId,
        String filePath
) {

    public static BoardDetailResponseDto from(Board board) {
        return new BoardDetailResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getComments().stream()
                        .map(CommentResponseDto::from)
                        .toList(),
                board.getCreated(),
                board.getUserId(),
                board.getFilePath()
        );
    }
}
