package com.aop_practice.service;

public class OrderServiceImpl implements OrderService {
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
