package com.aop_practice.service;

import org.springframework.transaction.annotation.Transactional;

public class OrderServiceImpl implements OrderService {
    @Transactional
    @Override
    public String placeOrder(Item item) {
        sleep(80);
        return "주문 완료: " + item.name();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
