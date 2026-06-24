package com.springtheory.ch01.ex_1_7.dao;

import com.springtheory.ch01.ex_1_3.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

// * 직접 만든 싱글톤(자바 코드로 구현하는 전통적인 싱글톤 패턴 - GoF)
// 만드는 순서
//  1) 클래스당 오브젝트를 1개만 담아둘 자기 자신 타입의 static 필드를 만든다.
//  2) 생성자를 private으로 막아 외부에서 new 로 마구 만들지 못하게 한다.
//  3) 유일한 오브젝트를 돌려주는 static 메서드(getInstance)를 둔다.
//     - 처음 호출될 때 한 번만 만들고, 그 다음부터는 이미 만든 오브젝트를 그대로 돌려준다.

// * 직접 만든 싱글톤의 한계
// - private 생성자라 상속이 불가능 (객체 지향의 장점인 상속 / 다형성을 못쓴다)
// - 테스트가 어렵다 (생성 방식이 고정되어 가짜(목업) 오브젝트로 갈아끼우기 힘들다.)
// - getInstance 처럼 의존 오브젝트(SimpleConnectionMaker) 주입이 어색하다.
// - 즉, 두 번째 호출부터는 호출 인자가 무시된다.
// - 클래스 로더가 여래 개인 환경에서는 싱글톤이 여러개 생길 수 있다.
// - static 필드라 사실상 전역 상태가 되어 아무 데서나 접근 가능해진다.

public class UserDAO {
    // 1) 클래스가 자기 자신의 유일한 오브젝트를 보관 (클래스당 1개)
    private final SimpleConnectionMaker connectionMaker;
    private static UserDAO instance;

    // 2) 생성자를 private 으로 막는다 -> 이제 외부에서 new UserDAO(...) 불가
    private UserDAO(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    // 3) 유일한 오브젝트를 돌려주는 통로
    // - synchronized: 여러 스레드가 동시에 들어와 오브젝트가 2개 만들어지는 것을 막는다.
    public static synchronized UserDAO getInstance(SimpleConnectionMaker connectionMaker) {
        if (instance == null) {
            instance = new UserDAO(connectionMaker);
        }
        return instance;
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
