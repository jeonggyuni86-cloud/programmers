package org.example.boardservice.service;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.config.security.AuthenticatedUser;
import org.example.boardservice.config.security.CurrentUserProvider;
import org.example.boardservice.domain.entity.Board;
import org.example.boardservice.domain.entity.Comment;
import org.example.boardservice.domain.repository.BoardRepository;
import org.example.boardservice.domain.repository.CommentRepository;
import org.example.boardservice.dto.CommentWriteRequestDto;
import org.example.boardservice.exception.BoardAccessDeniedException;
import org.example.boardservice.exception.BoardNotFoundException;
import org.example.boardservice.exception.CommentNotFoundException;
import org.example.boardservice.exception.InvalidRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public void addComment(Long boardId, CommentWriteRequestDto request) {
        if (request == null || request.content() == null || request.content().isBlank()) {
            throw new InvalidRequestException("댓글 내용을 입력해주세요.");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));
        AuthenticatedUser user = currentUserProvider.getCurrentUser();

        Comment comment = Comment.create(request.content().trim(), user.userId(), board);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long boardId, Long commentId) {
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        AuthenticatedUser user = currentUserProvider.getCurrentUser();

        if (!user.isAdmin() && !comment.getUserId().equals(user.userId())) {
            throw new BoardAccessDeniedException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
