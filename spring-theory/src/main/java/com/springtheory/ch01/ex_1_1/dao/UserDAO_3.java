package com.springtheory.ch01.ex_1_1.dao;

import com.springtheory.ch01.ex_1_1.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

// UserDAO -> 다른 회사가 구매 희망 한다 가정
// N, D사가 각각 다른 DB 사용 희망
// 더 나아가 UserDAO 구매 후, DB 커넥션 가져오는 방법 변경 가능

// -> 고객에게 미리 컴파일 된 클래스 바이너리 파일만 제공하고 싶다
// -> 고객 스스로 커넥션 방식을 적용 하며 UserDAO를 사용하게 할 수 있다.
// -> 추상화 필요


// 상속을 통한 확장
// 클래스 계층구조를 통해 두 개의 관심이 독립적으로 분리되면서 변경 작업이 용이해졌다
// 새로운 DB 연결 방법을 적용해야 할 때는 userDAO 상속을 통해 확장해주기만 하면 된다.
// 그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 메서드 등으로 만든다
// -> 템플릿 메서드 패턴

// 서브클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것을 '팩토리 메서드 패턴' 이라 한다
// getConnection() 메서드에서 생성하는 Connection 오브젝트의 구현 클래스는 제각각이지만
// UserDAO 는 Connection 인터페이스 타입의 오브젝트라는 것 외엔 관심이 없다.

// 선택적으로 오버라이딩 가능하게 만든 메서드를 '훅 메서드' 라 한다.

public abstract class UserDAO_3 {

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

    // UserDAO의 소스코드를 제공하면, getConnection 메서드를 원하는 방향으로 확장한 후
    // UserDAO의 기능과 함께 사용할 수 있다.

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
