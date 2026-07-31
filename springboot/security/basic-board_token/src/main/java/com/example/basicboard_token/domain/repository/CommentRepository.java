package com.example.basicboard_token.domain.repository;

import com.example.basicboard_token.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
