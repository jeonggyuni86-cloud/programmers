package com.basicboard.mapper;

import com.basicboard.domain.entity.Board;
import com.basicboard.domain.entity.Comment;
import com.basicboard.dto.BoardWithCommentsResponseDto;
import com.basicboard.dto.CommentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public BoardWithCommentsResponseDto toBoardWithCommentsResponseDto(Board board) {
        var comments = board.getComments().stream()
                .map(this::toCommentResponseDto)
                .toList();

        return BoardWithCommentsResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .comments(comments)
                .build();
    }

    private CommentResponseDto toCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .build();
    }
}
