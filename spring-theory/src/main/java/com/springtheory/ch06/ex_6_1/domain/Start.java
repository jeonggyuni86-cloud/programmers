package com.springtheory.ch06.ex_6_1.domain;

// * 문제점
// 메서드 마다 새로운 구현 클래스를 만들어야 한다.

// '로컬 클래스'
// -> UserDAO add, deleteAll 참고
// '익명 클래스'


import com.springtheory.ch05.ex_5_2.dao.DConnectionMaker;
import com.springtheory.ch06.ex_6_1.dao.JdbcContext;
import com.springtheory.ch06.ex_6_1.dao.UserDAO;

import java.sql.SQLException;

public class Start {
    static void main(String[] args) throws SQLException, ClassNotFoundException {
        DConnectionMaker connectionMaker = new DConnectionMaker();
        JdbcContext context = new JdbcContext(connectionMaker);
        UserDAO dao = new UserDAO(context);

        System.out.println(dao.getCount());
    }
}
