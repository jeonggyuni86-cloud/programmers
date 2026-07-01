package com.springtheory.ch06.ex_6_1.dao;

import com.springtheory.ch06.ex_6_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// * 레벨 관리를 위해 확장된 UserDAo
// - 등록, 수정, 조회를 제공한다
// - 모든 메서드는 직접 커넥션을 다루지 않고, 변하지 않는 흐름은 jdbcContext에 맡긴다.
// (실행계 SQL은 statementStrategy, 조회 결과 매핑은 RowMapper에 분리, 위임한다)

public class UserDAO {
    private final JdbcContext jdbcContext;

    public UserDAO(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public RowMapper<User> userRowMapper = new  RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };

    public void add(User user) throws  SQLException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                var pStmt = connection.prepareStatement("INSERT INTO users (id, name, password, level, login, recommend) VALUES (?, ?, ?, ?, ?, ?)");

                pStmt.setString(1, user.getId());
                pStmt.setString(2, user.getName());
                pStmt.setString(3, user.getPassword());
                pStmt.setInt(4, user.getLevel().getValue());
                pStmt.setInt(5, user.getLogin());
                pStmt.setInt(6, user.getRecommend());

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

    public User get(String id) throws SQLException, ClassNotFoundException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                var pStmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                pStmt.setString(1, id);
                return pStmt;
            }
        };
        return jdbcContext.queryForObject(strategy, userRowMapper);
    }

    public List<User> getAll() throws SQLException, ClassNotFoundException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("SELECT * FROM users ORDER BY id");
            }
        };
        return jdbcContext.query(strategy, userRowMapper);
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("SELECT COUNT(*) FROM users");
            }
        };
        return jdbcContext.queryForObject(strategy, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }
        });
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        var strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                var pstmt = conn.prepareStatement(
                        "UPDATE users SET name = ?, password = ?, level = ?, login = ?, recommend = ? WHERE id = ?");
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getPassword());
                pstmt.setInt(3, user.getLevel().getValue());
                pstmt.setInt(4, user.getLogin());
                pstmt.setInt(5, user.getRecommend());
                pstmt.setString(6, user.getId());

                return pstmt;
            }
        };
        jdbcContext.workWithStatementStrategy(strategy);
    }

}
