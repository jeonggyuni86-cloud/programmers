package com.springtheory.ch01.ex_1_5.dao;

public class AccountDao {
    private final SimpleConnectionMaker connectionMaker;

    public AccountDao(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
