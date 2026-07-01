package com.springtheory.ch06.ex_6_2.dao;


import com.springtheory.ch06.ex_6_2.service.TransactionAdvice;
import com.springtheory.ch06.ex_6_2.service.UserService;
import com.springtheory.ch06.ex_6_2.service.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {

    // UserService 빈 -> ProxyFactoryBean이 생산하는 프록시
    //  - target과 advisor만 등록하면, 스프링이 프록시를 알아서 만들어준다.
    //  - 여러 advisor를 addAdvisor로 얹을 수도 있다(부가기능 여러 개 조합).

    @Bean
    public ProxyFactoryBean userService() {
        var proxyFactoryBean = new ProxyFactoryBean();

        proxyFactoryBean.setTarget(userServiceImpl());
        proxyFactoryBean.addAdvice(transactionAdvice());

        return proxyFactoryBean;
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