package com.springtheory.ch06.ex_6_4.service;

import com.springtheory.ch06.ex_6_4.dao.Level;
import com.springtheory.ch06.ex_6_4.dao.UserDAO;
import com.springtheory.ch06.ex_6_4.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {
    // 업그레이드 기준값을 상수로 둔다.
    //  - 매직 넘버(50, 30)를 코드 곳곳에 흩지 않고 한곳에서 의미를 드러낸다.
    //  - 기준이 바뀌면 여기만 고치면 된다(변경 지점의 집중).
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 신규 가입
    public void add(User user) throws SQLException {
        user.setLevel(Level.BASIC);
        userDAO.add(user);
    }


    // 업그레이드 담당
    // 여러 사용자의 업그레이드를 '하나의 트랜젝션'으로 묶는다.
    // 트랜젝션 경계 설정
    // 트렌젝션 시작을 선언하고 commit 또는 Rollback으로 트랜젝션을 종료하는 작업

    @Transactional
    @Override
    public void upgradeLevels() throws SQLException, ClassNotFoundException {
        List<User> users = userDAO.getAll();
        for (var user : users) {
            if (canUpgrade(user)) {
                upgradeLevel(user);
            }
        }
    }
    // '올릴 수 있는가'
    private boolean canUpgrade(User user) {
        Level cur = user.getLevel();
        return switch (cur) {
            case BASIC -> user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER -> user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD -> false;
        };
    }

    // 실제 업그레이드
    protected void upgradeLevel(User user) throws SQLException, ClassNotFoundException {
        user.upgradeLevel();
        userDAO.update(user);
    }
}
