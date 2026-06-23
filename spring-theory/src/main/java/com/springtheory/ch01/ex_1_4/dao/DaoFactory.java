package com.springtheory.ch01.ex_1_4.dao;

public class DaoFactory {
    public UserDAO getUserDAO() {
        return new UserDAO(getConnectionMaker());
    }

    public AccountDao getAccountDAO() {
        return new AccountDao(getConnectionMaker());
    }

    public MessageDao getMessageDAO() {
        return new MessageDao(getConnectionMaker());
    }

    private SimpleConnectionMaker getConnectionMaker() {
        return new DConnectionMaker();
    }
}
