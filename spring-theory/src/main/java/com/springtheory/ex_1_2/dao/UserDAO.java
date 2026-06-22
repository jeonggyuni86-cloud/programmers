package com.springtheory.ex_1_2.dao;

import com.springtheory.ex_1_2.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

// 문제
// 상속은 1개 밖에 안되는 문제가 된다.
// -> 자식 클래스가 다른 기능을 상속 받을 수 없다.
// 상속을 통한 하위 클래스의 관계는 생각보다 밀접하다
// 상속 관계는 두 가징 관심사에 대해 긴밀한 결합을 허용한다.
// -> CRUD와 연결 관심사가 섞인다.
// 서브 클래스는 부모 클래스의 모든 메서드를 사용할 수 있다
// 그래서 슈퍼 클래스의 내부의 변경이 있을때 모든 서브 클래스를 수정하거나,
// 최악의 경우 다시 개발해야 할 수도 있다.

// '클래스의 분리'
// DB커넥션과 관련된 부분을 서브 클래스가 아닌 별도의 클래스에 담는다


public abstract class UserDAO {
    private final SimpleConnectionMaker connectionMaker;

    public UserDAO() {
        this.connectionMaker = new SimpleConnectionMaker();

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
