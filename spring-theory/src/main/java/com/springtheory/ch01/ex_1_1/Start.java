package com.springtheory.ch01.ex_1_1;

import com.springtheory.ch01.ex_1_1.dao.UserDAO;
import com.springtheory.ch01.ex_1_1.domain.User;

import java.sql.SQLException;

public class Start {
    static void main(String[] args) throws SQLException, ClassNotFoundException {
        var dao = new UserDAO();
        User user = new User();

        user.setId("test1");
        user.setName("test1");
        user.setPassword("123456");

        dao.add(user);
    }
}
