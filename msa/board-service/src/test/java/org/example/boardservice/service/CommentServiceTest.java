package org.example.boardservice.service;

import org.example.boardservice.config.security.AuthenticatedUser;
import org.example.boardservice.config.security.CurrentUserProvider;
import org.example.boardservice.domain.entity.Board;
import org.example.boardservice.domain.entity.Comment;
import org.example.boardservice.domain.repository.BoardRepository;
import org.example.boardservice.domain.repository.CommentRepository;
import org.example.boardservice.dto.CommentWriteRequestDto;
import org.example.boardservice.exception.BoardAccessDeniedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private CommentService commentService;

    @Mock
    private Board board;

    @Mock
    private Comment comment;

    @Test
    void usesAuthenticatedUserIdWhenAddingComment() {
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        when(currentUserProvider.getCurrentUser()).thenReturn(
                new AuthenticatedUser(10L, "token-user", "토큰 사용자", "ROLE_USER")
        );

        commentService.addComment(1L, new CommentWriteRequestDto("  댓글 내용  "));

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment saved = captor.getValue();
        assertEquals("token-user", saved.getUserId());
        assertEquals("댓글 내용", saved.getContent());
        assertSame(board, saved.getBoard());
    }

    @Test
    void rejectsDeletingAnotherUsersComment() {
        when(commentRepository.findByIdAndBoardId(3L, 1L))
                .thenReturn(Optional.of(comment));
        when(comment.getUserId()).thenReturn("owner");
        when(currentUserProvider.getCurrentUser()).thenReturn(
                new AuthenticatedUser(20L, "someone-else", "다른 사용자", "ROLE_USER")
        );

        assertThrows(
                BoardAccessDeniedException.class,
                () -> commentService.deleteComment(1L, 3L)
        );
        verify(commentRepository, never()).delete(comment);
    }
}
