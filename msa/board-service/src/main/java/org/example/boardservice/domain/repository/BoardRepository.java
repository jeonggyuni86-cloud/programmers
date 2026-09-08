package org.example.boardservice.domain.repository;

import org.example.boardservice.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @Query("""
            select distinct b
            from Board b
            left join fetch b.comments
            where b.id = :id
            """)
    Optional<Board> findWithCommentsById(Long id);
}
