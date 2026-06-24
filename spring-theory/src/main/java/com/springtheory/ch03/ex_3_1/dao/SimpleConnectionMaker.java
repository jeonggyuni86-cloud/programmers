package com.springtheory.ch03.ex_3_1.dao;

import java.sql.Connection;
import java.sql.SQLException;

// interface 를 사용하는 UserDAO 입장에서
// 어떤 클래스로 만들어졌는지 상관 없이 makeConnecion()을 호출하면
// Connection 타입의 오브젝트를 돌려줄 것으로 기대할 수 있다.


public interface SimpleConnectionMaker {
    Connection makeNewConnection() throws ClassNotFoundException, SQLException;

}
