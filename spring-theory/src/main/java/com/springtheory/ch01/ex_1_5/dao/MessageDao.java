package com.springtheory.ch01.ex_1_5.dao;

public class MessageDao {
    private final SimpleConnectionMaker connectionMaker;
    public MessageDao(SimpleConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
