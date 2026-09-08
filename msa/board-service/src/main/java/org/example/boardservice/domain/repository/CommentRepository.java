package org.example.boardservice.domain.repository;

import org.example.boardservice.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndBoardId(Long id, Long boardId);
}
