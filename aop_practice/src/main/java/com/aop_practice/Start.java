package com.aop_practice;

import com.aop_practice.config.AopConfig;
import com.aop_practice.service.Item;
import com.aop_practice.service.OrderService;
import com.aop_practice.service.OrderServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Start {
    static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AopConfig.class);

        var orderService = ctx.getBean(OrderService.class);
        var item = Item.builder().name("기게식키보드").build();

        System.out.println(orderService.placeOrder(item));

        System.out.println(orderService.getClass());
        System.out.println(orderService instanceof OrderServiceImpl);
    }
}
