package com.example.formlogin_practice.config;

import com.example.formlogin_practice.security.handler.CustomAuthenticationFailureHandler;
import com.example.formlogin_practice.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        configureAuthorization(http);
        configureLogin(http);
        configureLogout(http);
        return http.build();
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/join",
                                "/api/users/join",
                                "/css/**",
                                "/js/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
    }

    private void configureLogin(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
                .loginPage("/users/login")
                .loginProcessingUrl("/users/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                );
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        http
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
