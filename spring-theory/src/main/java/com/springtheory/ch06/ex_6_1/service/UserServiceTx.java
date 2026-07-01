package com.springtheory.ch06.ex_6_1.service;

import com.springtheory.ch06.ex_6_1.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

public class UserServiceTx implements UserService {
    private final PlatformTransactionManager transactionManager;
    private final UserServiceImpl userService;

    public UserServiceTx(PlatformTransactionManager transactionManager, UserServiceImpl userService) {
        this.transactionManager = transactionManager;
        this.userService = userService;
    }
    @Override
    public void add(User user) throws SQLException {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        var status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
            transactionManager.commit(status);
        } catch(Exception e) {
            transactionManager.rollback(status);
        }
    }
}
