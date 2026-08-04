package com.example.oauth2_basic_board.mapper;

import com.example.oauth2_basic_board.domain.entity.Board;
import com.example.oauth2_basic_board.domain.entity.Comment;
import com.example.oauth2_basic_board.dto.request.BoardWriteRequest;
import com.example.oauth2_basic_board.dto.response.BoardDetailResponse;
import com.example.oauth2_basic_board.dto.response.CommentResponse;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public BoardDetailResponse toBoardDetailResponse(Board board) {
        return BoardDetailResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .createdAt(board.getCreatedAt())
                .filePath(board.getFilePath())
                .comments(
                        board.getComments().stream()
                                .map(this::toCommentResponse)
                                .toList()
                )
                .build();
    }

    private CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public Board toEntity(BoardWriteRequest request, String userId, String filePath) {
        return Board.from(
                request.title(),
                request.content(),
                userId,
                filePath
        );
    }
}
