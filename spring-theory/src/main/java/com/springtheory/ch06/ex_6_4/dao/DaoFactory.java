package com.springtheory.ch06.ex_6_4.dao;

import com.springtheory.ch06.ex_6_4.service.UserService;
import com.springtheory.ch06.ex_6_4.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
// * @EnableTransactionManagement
//  - 선언적 트랜잭션(@Transactional)을 켜는 스위치.
//  - 이 한 줄이 6.5에서 손수 등록하던 것들(자동 프록시 생성기 + 트랜잭션 Advisor 등)을
//    스프링이 내부적으로 대신 등록해준다.
//  => DaoFactory에서 Advice/Pointcut/Advisor/AutoProxyCreator 빈이 전부 사라졌다!
@EnableTransactionManagement
public class DaoFactory {

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean // 오브젝트 생성을 담당하는 IoC용 메서드
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }


    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    @Bean
    public UserServiceImpl userServiceImpl() {return new UserServiceImpl(userDAO());}


    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("root");
        dataSource.setPassword("qwer1234");
        return dataSource;
    }

}