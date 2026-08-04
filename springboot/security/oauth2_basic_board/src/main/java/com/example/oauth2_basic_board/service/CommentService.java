package com.example.oauth2_basic_board.service;

import com.example.basicboard_token.config.security.CustomUserDetails;
import com.example.basicboard_token.domain.entity.Board;
import com.example.basicboard_token.domain.entity.Comment;
import com.example.basicboard_token.domain.entity.Member;
import com.example.basicboard_token.domain.entity.Role;
import com.example.basicboard_token.domain.repository.CommentRepository;
import com.example.basicboard_token.dto.request.CommentWriteRequest;
import com.example.basicboard_token.exception.BoardAccessDeniedException;
import com.example.basicboard_token.mapper.CommentMapper;
import com.example.basicboard_token.service.component.BoardHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Comment comment = commentMapper.toEntity(request, getLoginMember().getUserId(), board);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long boardId, long commentId) {
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        Member member = getLoginMember();
        if (member.getRole() != Role.ROLE_ADMIN
                && !comment.getUserId().equals(member.getUserId())) {
            throw new BoardAccessDeniedException("댓글 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }

    private Member getLoginMember() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }

}
