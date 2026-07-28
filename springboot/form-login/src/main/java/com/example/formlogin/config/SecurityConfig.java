package com.example.formlogin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// * 폼 로그인이란?
// 개발자가 직접 만든 HTML 로그인 화면(폼)을 통해 아이디/비밀번호를 받아 인증하는 방식이다.
// HTTP Basic이 브라우저 기본 팝업 + 헤더 방식이었다면, 폼 로그인은 우리가 디자인한 페이지 + 세션 기반이라는 점이 가장 큰 차이.
// 사람이 사용하는 일반적인 웹 애플리케이션의 표준 방식이다.

// * 전체 동작 흐름
// 로그인부터 그 이후 흐름까지의 과정
// 1. 보호된 자원 접근 -> 로그인 페이지로 리다이렉트
// 인증 안 된 사용자가 보호된 페이지에 접근하면, 로그인 페이지로 (/login)로 리다이렉트된다.
// 이 처리는 AuthenticationEntryPoint(폼 로그인용 구현체 LoginUrlAuthenticationEntryPoint)가 담당
// 2. 사용자가 폼에 입력하고 제출
// 로그인 화면에서 아이디/비밀번호를 입력하고 제출하면, 자격증명이 헤더가 아니라 요청 본문(body)에 파라미터로 담겨 POST /login으로 전송된다
// POST /login
// Content-Type: application/x-www-form-urlencoded
// username =userId&password=1234
// 3. UsernamePasswordAuthenticationFilter가 가로챔
// POST /login 요청을 UserNamePasswordAuthenticationFilter가 낚아채서 폼 로그인의 진입점이다.
// body에서 username, password를 꺼내 아직 인증되지 않은 UsernamePasswordAuthenticationToken을 만든다.
// 4. 인증 검증 (공통 흐름)
// AuthenticationManager (ProviderManager)
//    → DaoAuthenticationProvider   ← 지금 이 부분
//         ├─ UserDetailsService  (사용자 조회)
//         └─ PasswordEncoder     (비밀번호 대조)
// 5. 인증 성공 → 세선 셩공
// 안중애 성공하면, 인증된 Authentication을 SecurityContext에 담고 이걸 HttpSession에 저장한다.
// 그리고 서버는 SessionID를 JSESSIONID라는 쿠키로 브라우저에 내려준다.
// 성공 후 처리는 AuthenticationSuccessHandler가 담당 (기본적으로 원래 가려던 페이지 또는 지정된 페이지로 리다이렉트)
// 실패 시에는 AuthenticationFailerHandler가 담당 (보통 /login?error로 되돌림)
// 6. 이후 요청 -> 쿠키로 인증 유지
// 한 번 로그인 하면, 그 다음 요청부터는 아이디/비밀번호를 다시 보내지 않는다.
// 브라우저가 자동으로 JSESSIONID 쿠키를 실어 보내고, 서버는 이 쿠키로 세션을 찾아 저장해 둔, SecurityContext를 복원한다. (이 복원은 SecurityContextHolderFilter 가 담당)
// 즉 상태 유지(Stateful) 방식이다. HTTP Basic은 매 요청 헤더를 보내는 무상태였던 것과 차이가 있다.

// * 인증(Authentication) 흐름
// 로그인 처럼 "너 누구냐"를 확인하는 과정이다.

// * 인가(Authorization) 흐름
// "인증된 사용자가 이 자원에 접근할 권한이 있냐"를 판단하는 과정이다.

// * 핵심 컴포넌트
// - SecurityFilterChain : 어떤 필터들을 어떤 순서로 태울지 정의
// - AuthenticationManger / AuthenticationProvider : 인증 검증
// - UserDetailService / UserDetails : 사용자 정보 조회
// - PasswordEncode : 비밀번호 검증
// - SecurityContextHolder : 읜증 결과 보관
// - AuthorizationManager : 인가 접근 권한 판단


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/join",
                                "/api/users/join",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                )
                .formLogin(
                        form -> form
                                .loginPage("/users/login")
                                .loginProcessingUrl("/users/login")
                                .usernameParameter("userId")
                                .passwordParameter("password")
                                // 인증 성공
                                // 인증 실패
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
