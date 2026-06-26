package com.springtheory.ch03.ex_3_3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAODeleteAll implements StatementStrategy {

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM users");
    }
}
