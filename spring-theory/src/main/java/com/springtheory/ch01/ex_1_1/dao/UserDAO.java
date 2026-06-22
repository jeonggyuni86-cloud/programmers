package com.springtheory.ch01.ex_1_1.dao;

import com.springtheory.ch01.ex_1_1.domain.User;

import java.sql.DriverManager;
import java.sql.SQLException;

//DAO -> 데이터 계층에 접근할 객체
//DB를 사용해 데이터를 조회하거나 전담하는 오브젝트
public class UserDAO {

    public void add(User user) throws ClassNotFoundException, SQLException {
        // mysql class 전체 이름
        // Class.forNmae() -> 해당 이름의 클래스를 찾아 로딩한다
        Class.forName("com.mysql.cj.jdbc.Driver");

        String query = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "root", "qwer1234");
                var pStat = conn.prepareStatement(query);
                ) {
            pStat.setString(1, user.getId());
            pStat.setString(2, user.getName());
            pStat.setString(3, user.getPassword());
            pStat.executeUpdate();
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT * FROM users WHERE id = ?";
        try(
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "root", "qwer1234");
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
}
