package com.springtheory.ch06.ex_6_3.dao;


import com.springtheory.ch06.ex_6_3.domain.User;
import com.springtheory.ch06.ex_6_3.service.TransactionAdvice;
import com.springtheory.ch06.ex_6_3.service.UserService;
import com.springtheory.ch06.ex_6_3.service.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    // * 자동 프록시 생성기
    // - 빈 후처리기다. 컨테이너가 빈을 만드는 '도중에' 끼어들어 가공한다
    // - 등록된 모든 Advisor의 Pointcut을 검사해서, 조건에 맞는 빈을 '자동으로 프록시로 바꿔치기' 한다
    //  => 더 이상 빈마다 ProxyFactoryBean을 일일이 설정하지 않아도 된다(ex_6_2의 반복이 사라짐).
    //     target 빈은 평범하게 등록만 해두면, 이 생성기가 알아서 프록시를 입혀준다.
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    // Advisor - Pointcut + Advice
    //  - 부가기능과 적용대상을 묶은 한 덩어리. ProxyFactoryBean에는 이 Advisor 단위로 등록한다.
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    //  - ex_6_1에서는 핸들러 안에서 startsWith로 직접 걸렀지만, 이제 그 책임이 Pointcut으로 분리됐다.
    //  - NameMatchMethodPointcut: 메서드 이름 패턴으로 매칭. "upgrade*" -> upgradeLevels 등.
    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        var nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedNames("upgrade*");
        return nameMatchMethodPointcut;
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