package com.springtheory.ch05.ex_5_2.service;

import com.springtheory.ch05.ex_5_2.dao.Level;
import com.springtheory.ch05.ex_5_2.dao.UserDAO;
import com.springtheory.ch05.ex_5_2.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.List;

// * UserService - 사용자 레벨 관리 '비즈니스 로직'을 담는 계층

@Service
public class UserService {
    // 업그레이드 기준값을 상수로 둔다.
    //  - 매직 넘버(50, 30)를 코드 곳곳에 흩지 않고 한곳에서 의미를 드러낸다.
    //  - 기준이 바뀌면 여기만 고치면 된다(변경 지점의 집중).
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    private final UserDAO userDAO;
    private final PlatformTransactionManager transactionManager;

    public UserService(UserDAO userDAO, PlatformTransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.transactionManager = transactionManager;
    }

    // 신규 가입
    public void add(User user) throws SQLException {
        user.setLevel(Level.BASIC);
        userDAO.add(user);
    }

    // 업그레이드 담당
    public void upgradeLevel() throws SQLException, ClassNotFoundException {
        List<User> users = userDAO.getAll();
        for(User user : users) {
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
