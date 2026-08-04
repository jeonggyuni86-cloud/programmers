package com.example.oauth2_basic_board.domain.repository;

import com.example.basicboard_token.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("""
        select b
        from Board b
        left join fetch b.comments
        where b.id = :id
    """)
    Optional<Board> findDetailById(Long id);
}
