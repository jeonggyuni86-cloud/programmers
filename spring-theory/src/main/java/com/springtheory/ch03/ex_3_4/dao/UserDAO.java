package com.springtheory.ch03.ex_3_4.dao;

import com.springtheory.ch03.ex_3_4.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserDAO {
    private final SimpleConnectionMaker connectionMaker;
    public UserDAO(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) {
        try (
                var conn = connectionMaker.makeNewConnection();
                var pStat = statementStrategy.makeStatement(conn);
        ) {
            pStat.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        jdbcContextWithStatementStrategy(strategy);
    }

    public User get(String id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try(
                var conn = connectionMaker.makeNewConnection();
                var pStat = conn.prepareStatement(query);
        ) {
            pStat.setString(1, id);
            var rs = pStat.executeQuery();

            rs.next();

            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // remove only test
    public void deleteAll() throws ClassNotFoundException, SQLException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("DELETE FROM users");
            }
        };
        jdbcContextWithStatementStrategy(strategy);
    }

}
