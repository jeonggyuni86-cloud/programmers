package com.springtheory.ch05.ex_5_2.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcContext {
    private final SimpleConnectionMaker connectionMaker;

    public JdbcContext(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) {
        try (
                var conn = connectionMaker.makeNewConnection();
                var pStat = statementStrategy.makeStatement(conn);
        ) {
            pStat.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 조회용 컨텍스트 : 여러 건을 조회해 리스트로 돌려준다.
    // - 제네릭 타입 T는 사용하기 전에 반드시 어딘가에서 선언해야 한다.
    // 1) 클래스 레벨 선언
    // 2) 메서드 레벨 선언
    //  - 변하지 않는 흐름: 커넥션 획득 -> executeQuery -> ResultSet을 한 줄씩 순회 -> 자원 정리.
    //  - 변하는 부분 둘: '어떤 SELECT인가(strategy)'와 '한 줄을 무엇으로 만들까(rowMapper)'.
    public <T> List<T> query(StatementStrategy strategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        try(
                var conn = connectionMaker.makeNewConnection();
                var pStmt = strategy.makeStatement(conn);
                var rs = pStmt.executeQuery();
                ) {

            var results = new ArrayList<T>();
            while(rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }
            return results;
        }
    }

    // 조회용 컨텍스트 : 정확히 한 건을 조회한다.
    // 결과가 없으면 의미 있는 예외(EmptyResultDataAccessException, ch04에서 배운 추상화 예외)를 던진다.
    public <T> T queryForObject(StatementStrategy statementStrategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        var results = query(statementStrategy, rowMapper);
        if(results.isEmpty())
            throw new EmptyResultDataAccessException(0);
        return results.getFirst();
    }

}
