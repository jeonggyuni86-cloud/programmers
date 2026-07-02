package com.aop_practice.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

public class PerformanceMonitorAdviceNanoMills implements MethodInterceptor, Performance{
    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        var name = invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName();
        long start = System.nanoTime();
        System.out.println("[PerformanceMonitorAdviceNanoMills] : start");
        try {
            return invocation.proceed();
        } finally {
            long end =  System.nanoTime();
            System.out.printf("[PERF] %s : %dns\n", name, end - start);
            System.out.println("[PerformanceMonitorAdviceNanoMills] : end");
        }
    }
}
