package com.example.oauth2_basic_board.mapper;


import com.example.oauth2_basic_board.domain.entity.Board;
import com.example.oauth2_basic_board.domain.entity.Comment;
import com.example.oauth2_basic_board.dto.request.CommentWriteRequest;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(
            CommentWriteRequest request,
            String userId,
            Board board
    ) {
        return Comment.from(
                request.content(),
                userId,
                board
        );
    }
}
