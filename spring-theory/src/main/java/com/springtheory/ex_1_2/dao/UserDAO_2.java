package com.springtheory.ex_1_2.dao;

import com.springtheory.ex_1_2.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

// 문제
// 상속을 해버리면 UserDAO의 코드가 SimpleConnectionMaker 특정 클래스에 종속 되어버린다.
// 유연성 저하
// 매 커넥션을 제공하는 클래스가 어떤 정보를 가지고 있는지 알고 있어야 한다
// 즉, 구체 클래스의 의존성을 낮춰야한다

// -> interface 도입
// 가장 좋은 해결책은 두 개의 클래스가 서로 긴밀하게 연결되어 있지 않도록 함
// 중간에 추상적인 느슨한 연결고리를 만들어주는 것이다.
// 추상화란 어떤 것들의 공통적인 성격을 뽑아내어 이를 따로 분리해내는 작업이다.
// 인터페이스를 통해 접근하면 실제 구현 클래스르 바꿔도 신경쓸 일이 없다.

public abstract class UserDAO_2 {
    private final SimpleConnectionMaker_2 connectionMaker;

    public UserDAO_2() {
        this.connectionMaker = new DConnectionMaker_2();
    }

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

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        return this.connectionMaker.makeNewConnection();
    }

}
