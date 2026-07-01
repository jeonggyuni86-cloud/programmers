package com.aop_practice.config;

import com.aop_practice.service.OrderService;
import com.aop_practice.service.OrderServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public PerformanceMonitorAdvice performanceMonitorAdvice() {
        return new PerformanceMonitorAdvice();
    }
    @Bean
    public Advisor performanceAdvisor() {
        var pointCut = new AspectJExpressionPointcut();
        pointCut.setExpression("execution(* com.aop_practice.service..*.*(..))");
        return new DefaultPointcutAdvisor(pointCut, performanceMonitorAdvice());
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator autoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }
}
