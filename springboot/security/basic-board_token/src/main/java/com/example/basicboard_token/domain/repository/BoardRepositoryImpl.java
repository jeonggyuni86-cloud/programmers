package com.example.basicboard_token.domain.repository;

import com.example.basicboard_token.domain.entity.QBoard;
import com.example.basicboard_token.domain.entity.QComment;
import com.example.basicboard_token.domain.entity.QMember;
import com.example.basicboard_token.dto.request.BoardSearchRequest;
import com.example.basicboard_token.dto.response.BoardAuthorStatResponse;
import com.example.basicboard_token.dto.response.BoardListItemResponse;
import com.example.basicboard_token.dto.response.QBoardAuthorStatResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private static final QBoard board = QBoard.board;
    private static final QComment comment = QComment.comment;
    private static final QMember member = QMember.member;

    @Override
    public Page<BoardListItemResponse> searchBoard(BoardSearchRequest condition, Pageable pageable) {
        var content = queryFactory
                .select(
                        Projections.constructor(
                                BoardListItemResponse.class,
                                board.id,
                                board.title,
                                board.userId,
                                member.userName,
                                commentCountOf(),
                                board.createdAt
                        )
                )
                .from(board)
                .leftJoin(member).on(board.userId.eq(member.userId))
                .where(
                        titleContains(condition.title()),
                        userIdEquals(condition.userId()),
                        createdGoe(condition.from()),
                        createdLoe(condition.to())
                )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        titleContains(condition.title()),
                        userIdEquals(condition.userId()),
                        createdGoe(condition.from()),
                        createdLoe(condition.to())
                );
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<BoardAuthorStatResponse> countByAuthor(long minCount) {
        return queryFactory.select(new QBoardAuthorStatResponse(
                        board.userId,
                        member.userName,
                        board.count()
                ))
                .from(board)
                .leftJoin(member).on(board.userId.eq(member.userId))
                .groupBy(board.userId, member.userName)
                .having(board.count().goe(minCount))
                .orderBy(board.count().desc())
                .fetch();
    }

    private Expression<Long> commentCountOf() {
        return JPAExpressions
                .select(comment.count())
                .from(comment)
                .where(comment.board.id.eq(board.id));
    }

    private BooleanExpression titleContains(String title) {
        return (title == null || title.isBlank())
                ? null
                : board.title.contains(title);
    }

    // 작성자 아이디 정확히 일치. 빈 값이면 조건 없음(null)
    private BooleanExpression userIdEquals(String userId) {
        return (userId == null || userId.isBlank()) ? null : board.userId.eq(userId);
    }

    private BooleanExpression createdGoe(LocalDate from) {
        return from == null ? null : board.createdAt.goe(from.atStartOfDay());
    }
    private BooleanExpression createdLoe(LocalDate to) {
        return to == null ? null : board.createdAt.loe(to.atTime(LocalTime.MAX));
    }
}

