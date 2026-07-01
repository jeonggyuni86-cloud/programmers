package com.aop_practice.config;

import com.aop_practice.service.OrderService;
import com.aop_practice.service.OrderServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public Pointcut pointcut() {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.aop_practice.service..*.*(..))");
        // *  : 현재 위치에서 아무거나 하나
        // .. : 재귀적으로 전부(패키지) / 개수 제한 없음(매개변수)

        // 맨앞 * <- 리턴 타입 무관
        // service 이하 모든 패키지
        // 모든 클래스
        // 모든 메서드
        // 매개변수 무관
        // 맨앞 * <- 리턴 타입 무관

        // * com.example.shop.service..*Service.get*(..)
        // service 이하 모든 패키지
        // Service로 끝나는 클래스
        // get으로 시작하는 메서드
        // 매개변수 무관
        return pointcut;
    }

    @Bean
    public PerformanceMonitorAdvice getPerformanceMonitorAdvice() {
        return new PerformanceMonitorAdvice();
    }

    @Bean
    public PerformanceMonitorAdviceNanoMills getPerformanceMonitorAdviceNanoMills() {
        return new PerformanceMonitorAdviceNanoMills();
    }

    /*
    @Bean
    public Advisor performanceAdvisor(Pointcut pointcut, PerformanceMonitorAdvice performanceMonitorAdvice) {
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorAdvice);
    }
    */

    @Bean
    public Advisor performanceNanoAdvisor(Pointcut pointcut, PerformanceMonitorAdviceNanoMills performanceMonitorAdviceNanoMills) {
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorAdviceNanoMills);
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
