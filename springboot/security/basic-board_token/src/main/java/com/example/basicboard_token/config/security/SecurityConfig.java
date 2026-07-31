package com.example.basicboard_token.config.security;

import com.example.basicboard_token.config.filter.TokenAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        init(http);
        configureAuthorization(http);
        addFilter(http);
        exceptionHandling(http);
        return http.build();
    }

    private void init(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureAuthorization(HttpSecurity http) {
        http.authorizeHttpRequests(authorize -> authorize

                .requestMatchers(
                        "/",
                        "/write",
                        "/detail",
                        "/update/**",

                        "/members/join",
                        "/members/login",

                        "/api/members/join",
                        "/api/members/login",

                        "/css/**",
                        "/js/**"
                ).permitAll()

                .requestMatchers(HttpMethod.GET,
                        "/api/boards",
                        "/api/boards/**"
                ).permitAll()

                .requestMatchers("/stats")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
        );
    }

    private void addFilter(HttpSecurity http) {
        http
                .addFilterBefore(
                        tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    private void exceptionHandling(HttpSecurity http) {
        http
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                );
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            if(request.getRequestURI().startsWith("/api")) {
                sendError(response, HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다");
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            if(request.getRequestURI().startsWith("/api")) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다");
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix() // 접두사 자동 부착"ROLE_"
                .role("ADMIN").implies("USER")
                .build();
    }


    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("status code : " + status + ", message : " + message);
    }
}
