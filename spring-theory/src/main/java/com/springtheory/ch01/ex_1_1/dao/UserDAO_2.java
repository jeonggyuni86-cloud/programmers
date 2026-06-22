package com.springtheory.ch01.ex_1_1.dao;

import com.springtheory.ch01.ex_1_1.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAO_2 {

    public void add(User user) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";
        try (
                var conn = getConnection();
                var pStat = conn.prepareStatement(query);
        ) {
            pStat.setString(1, user.getId());
            pStat.setString(2, user.getName());
            pStat.setString(3, user.getPassword());
            pStat.executeUpdate();
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try(
                var conn = getConnection();
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
        }
    }

    // 중복 코드의 메서드 추출
    // 리팩토링 : 기존의 코드를 외부의 동작 변화 없이 내부 구조 변경
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "root", "qwer1234");
    }
}
