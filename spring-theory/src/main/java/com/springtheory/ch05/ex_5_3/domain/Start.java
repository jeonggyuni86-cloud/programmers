package com.springtheory.ch05.ex_5_3.domain;

// * 문제점
// 메서드 마다 새로운 구현 클래스를 만들어야 한다.

// '로컬 클래스'
// -> UserDAO add, deleteAll 참고
// '익명 클래스'


import com.springtheory.ch05.ex_5_3.dao.JdbcContext;
import com.springtheory.ch05.ex_5_3.dao.UserDAO;

import java.sql.SQLException;

public class Start {
    static void main(String[] args) throws SQLException, ClassNotFoundException {

    }
}
