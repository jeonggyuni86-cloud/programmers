package com.springtheory.ch03.ex_3_3.dao;

import com.springtheory.ch03.ex_3_3.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAOAdd implements StatementStrategy {
    private final User user;

    public UserDAOAdd(User user) {
        this.user = user;
    }
    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        var pStmt = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?, ?, ?)");

        pStmt.setString(1, user.getId());
        pStmt.setString(2, user.getName());
        pStmt.setString(3, user.getPassword());

        return pStmt;
    }
}
