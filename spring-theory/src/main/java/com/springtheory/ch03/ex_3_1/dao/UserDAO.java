package com.springtheory.ch03.ex_3_1.dao;

import com.springtheory.ch03.ex_3_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// * 템플릿 메서드 패턴의 적용
// 상속을 통해 기능을 확장해서 사용하는 부분이다.
// 변하지 않는 부분은 슈퍼클래스에 두고 변하는 부분은 추상메서드로 정의해줘서
// 서브클래스에서 오버라이드 하여 새롭게 정의해서 쓰도록 한다.

public abstract class UserDAO {
    private SimpleConnectionMaker connectionMaker;
    protected UserDAO() {

    }

    public UserDAO(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
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

    // remove only test
    public void deleteAll() throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM users";
        try(
                var conn = getConnection();
                var pStat = conn.prepareStatement(query);
        ) {
            pStat.executeUpdate();
        }
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        String query = "SELECT COUNT(*) FROM users";

        try (
                var conn = connectionMaker.makeNewConnection();
                var pStat = conn.prepareStatement(query);
                var resultSet = pStat.executeQuery();
        ) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    //deleteAll 한정
    //재사용 불가
    protected abstract PreparedStatement makeStatement(Connection conn) throws SQLException;

//    {
//        //        String query = "DELETE FROM users";
//        //        return conn.prepareStatement(query);}
//    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        return this.connectionMaker.makeNewConnection();
    }

}
