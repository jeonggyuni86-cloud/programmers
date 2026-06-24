package com.springtheory.ch03.ex_3_2.dao;

import com.springtheory.ch03.ex_3_2.domain.User;

import java.sql.Connection;
import java.sql.SQLException;


/*
 * * 전략 패턴의 적용
 * - 컨텍스트
 * 변하지 않는 부분 : JDBC 커넥션 / 실행 / 자원관리 공통 흐름
 * - 전략
 * 변하는 부분 : 어떤 PreparedStatement 만들지 -> interface로 추상화
 *
 * 컨텍스트는 '인터페이스(StatementStrategy)에만' 의존하고, 실제 전략은 런타임에 주입받는다.
 * 그래서 새 기능을 추가해도 컨텍스트 코드는 닫혀 있고(수정X) 전략만 새로 만들면 된다(확장O) = OCP.
 */
public class UserDAO {
    private final SimpleConnectionMaker connectionMaker;
    public UserDAO(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }


    // * add 전략 (StatementStrategy 구현체)
    // '변하는 부분'인 PreparedStatement 생성 로직만 담은 전략 클래스다.
    // deleteAll과 달리 add는 저장할 User 데이터가 필요하므로,
    // 생성자로 User를 받아 전략 안에서 파라미터까지 채워 완성된 statement를 돌려준다.
    //  - 커넥션을 얻고, 전달받은 '전략'에게 statement 생성을 맡기고, 실행하고, 자원을 정리한다.
    //  - 어떤 SQL을 실행할지는 전혀 모른다. 그건 strategy가 결정한다(인터페이스에만 의존).
    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) {
        try (
                var conn = connectionMaker.makeNewConnection();
                var pStat = statementStrategy.makeStatement(conn);
        ) {
            pStat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(User user) throws  SQLException {
        var add = new UserDAOAdd(user);
        jdbcContextWithStatementStrategy(add);
    }

    public User get(String id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try(
                var conn = connectionMaker.makeNewConnection();
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
        jdbcContextWithStatementStrategy(new UserDAODeleteAll());
    }

}
