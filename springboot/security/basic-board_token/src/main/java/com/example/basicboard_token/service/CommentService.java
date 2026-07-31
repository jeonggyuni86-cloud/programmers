package com.example.basicboard_token.service;

import com.example.basicboard_token.domain.entity.Board;
import com.example.basicboard_token.domain.entity.Comment;
import com.example.basicboard_token.domain.repository.CommentRepository;
import com.example.basicboard_token.dto.request.CommentWriteRequest;
import com.example.basicboard_token.mapper.CommentMapper;
import com.example.basicboard_token.service.component.BoardHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardHandler boardHandler;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(
            Long boardId,
            CommentWriteRequest request
    ) {
        Board board = boardHandler.getBoard(boardId);
        Comment comment = commentMapper.toEntity(request, board);
        commentRepository.save(comment);
    }

}
