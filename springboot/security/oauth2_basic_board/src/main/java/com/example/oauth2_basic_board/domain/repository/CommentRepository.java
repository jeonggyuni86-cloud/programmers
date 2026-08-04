package com.example.oauth2_basic_board.domain.repository;

import com.example.basicboard_token.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndBoardId(Long id, Long boardId);
}
