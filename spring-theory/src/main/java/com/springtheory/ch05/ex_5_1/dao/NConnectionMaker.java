package com.springtheory.ch05.ex_5_1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements SimpleConnectionMaker {
    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "root", "qwer1234");
    }
}
