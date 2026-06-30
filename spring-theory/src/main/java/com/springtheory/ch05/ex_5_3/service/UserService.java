package com.springtheory.ch05.ex_5_3.service;

import com.springtheory.ch05.ex_5_3.domain.User;

import java.sql.SQLException;

// UserServiceImpl : 순수 비즈니스 로직만
// UserServiceTx : 트랜젝션 경계만 책임지고 UserService로 위임

public interface UserService {
    void add(User user) throws SQLException;
    void upgradeLevels() throws SQLException, ClassNotFoundException;
}
