package com.example.basicboard_token.mapper;

import com.example.basicboard_token.domain.entity.Board;
import com.example.basicboard_token.domain.entity.Comment;
import com.example.basicboard_token.dto.request.CommentWriteRequest;
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
