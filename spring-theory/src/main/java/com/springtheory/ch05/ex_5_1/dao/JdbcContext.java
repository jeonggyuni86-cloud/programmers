package com.springtheory.ch05.ex_5_1.dao;

import java.sql.SQLException;

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

}
