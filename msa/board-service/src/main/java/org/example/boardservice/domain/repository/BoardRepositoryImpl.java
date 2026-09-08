package org.example.boardservice.domain.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.boardservice.domain.entity.QBoard;
import org.example.boardservice.domain.entity.QComment;
import org.example.boardservice.dto.BoardSearchRequestDto;
import org.example.boardservice.dto.BoardSearchRowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private static final QBoard board = QBoard.board;
    private static final QComment comment = QComment.comment;

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardSearchRowDto> searchBoards(
            BoardSearchRequestDto condition,
            Pageable pageable
    ) {
        List<BoardSearchRowDto> content = queryFactory
                .select(Projections.constructor(
                        BoardSearchRowDto.class,
                        board.id,
                        board.title,
                        board.userId,
                        commentCountOf(),
                        board.created
                ))
                .from(board)
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        titleContains(condition.getTitle()),
                        userIdEquals(condition.getUserId()),
                        createdGoe(condition.getFrom()),
                        createdLoe(condition.getTo())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, Optional.ofNullable(count).orElse(0L));
    }

    private BooleanExpression titleContains(String title) {
        return title == null || title.isBlank()
                ? null
                : board.title.containsIgnoreCase(title.trim());
    }

    private BooleanExpression userIdEquals(String userId) {
        return userId == null || userId.isBlank()
                ? null
                : board.userId.eq(userId.trim());
    }

    private BooleanExpression createdGoe(LocalDate from) {
        return from == null ? null : board.created.goe(from.atStartOfDay());
    }

    private BooleanExpression createdLoe(LocalDate to) {
        return to == null ? null : board.created.loe(to.atTime(LocalTime.MAX));
    }

    private Expression<Long> commentCountOf() {
        return JPAExpressions
                .select(comment.count())
                .from(comment)
                .where(comment.board.id.eq(board.id));
    }
}
