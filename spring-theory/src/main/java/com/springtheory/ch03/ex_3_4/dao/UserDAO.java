package com.springtheory.ch03.ex_3_4.dao;

import com.springtheory.ch03.ex_3_4.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserDAO {
    private final JdbcContext jdbcContext;

    public UserDAO(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) throws  SQLException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                var pStmt = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?, ?, ?)");

                pStmt.setString(1, user.getId());
                pStmt.setString(2, user.getName());
                pStmt.setString(3, user.getPassword());

                return pStmt;
            }
        };
        jdbcContext.workWithStatementStrategy(strategy);
    }

    // remove only test
    public void deleteAll() throws ClassNotFoundException, SQLException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("DELETE FROM users");
            }
        };
        jdbcContext.workWithStatementStrategy(strategy);
    }

}
