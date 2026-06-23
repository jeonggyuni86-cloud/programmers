package com.springtheory.ch01.ex_1_6.dao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDAO userDAO() {
        return UserDAO.getInstance(getConnectionMaker());
    }

    @Bean
    public SimpleConnectionMaker getConnectionMaker() {
        return new DConnectionMaker();
    }
}