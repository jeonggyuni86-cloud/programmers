package com.board_practice.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.board_practice.controller..*(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object logRequest(ProceedingJoinPoint joint) throws Throwable {
        String className = joint.getSignature().getDeclaringTypeName();
        String methodName = joint.getSignature().getName();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String httpInfo = "";
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpInfo = request.getMethod() + "." + request.getRequestURL();
        }

        System.out.println("[요청시작] " + httpInfo + "->" + methodName);

        long start = System.nanoTime();
        try {
            Object result = joint.proceed();

            long during = System.nanoTime() - start;
            System.out.printf("[요청완료] %s %.3fms\n", methodName, during / 1_000_000.0);
            return result;
        } catch(Exception e) {
            long during = System.nanoTime() - start;
            System.out.printf("[요청실패] %s %.3fms\n", methodName, during / 1_000_000.0);
            throw e;
        }
    }
}
