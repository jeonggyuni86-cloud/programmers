package com.springtheory.ch03.ex_3_4.dao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// DaoFactory를 스프링 빈 팩토리가 사용할 수 있는 설정 정보로 리팩토링
// Configuration 대신 Component 써도 되지만 유지 보수상 Configuration으로 사용
// 기능상 차이가 없지만 유지보수를 위해 구분지어 놓은 것

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {

    @Bean // 오브젝트 생성을 담당하는 IoC용 메서드
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public SimpleConnectionMaker getConnectionMaker() {
        return new DConnectionMaker();
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(getConnectionMaker());
    }


}