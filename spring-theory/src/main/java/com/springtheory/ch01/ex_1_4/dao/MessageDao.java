package com.springtheory.ch01.ex_1_4.dao;

public class MessageDao {
    private final SimpleConnectionMaker connectionMaker;
    public MessageDao(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
