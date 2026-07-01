package com.aop_practice.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

public class PerformanceMonitorAdvice implements MethodInterceptor {
    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        var name = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long end =  System.currentTimeMillis();
            System.out.printf("[PREF] %s : %dms\n", name, end - start);
        }
    }
}
