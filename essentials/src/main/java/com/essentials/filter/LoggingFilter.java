package com.essentials.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoggingFilter implements Filter {
    //필터 초기화: 필요시
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //필수
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain
    ) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;

        // 요청 정보 로깅
        System.out.println("Request URL : " + request.getRequestURL().toString());
        System.out.println("Request Method : " + request.getMethod());

        // 필터 체인: 계속해서 다음 필터 또는 서블릿으로 전달
        chain.doFilter(request, response);
    }

    //필터 종료시: 필요시
    @Override
    public void destroy() {

    }
}
