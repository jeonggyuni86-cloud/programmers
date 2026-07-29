package com.example.token.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// * JWT(JSON Web Token)
// JWT는 당사자 간에 정보를 JSON객체로 안전하게 전달하기 위한 토큰 표준
// '.' 으로 구 분된 세 부분으로 구성된다.
// xxxxx-yyyyy-zzzzz -> Header.payload.Signature

// 1. Header
// - 토큰의 메타 정보 : 서명 알고리즘(alg), 토큰 타입(typ)
// - 예) : {"alg : "HS256", "typ" : "JWT"}
// - 이 JSON을 Base64Url 인코딩 한 것이 첫 번쨰 부분

// 2. Payload
// - 실제 전달할 데이터만 클레임(Claim)들을 담는다.
// - 주의: 암호화가 아니라, '인코딩'일 뿐 -> 누구나 디코딩해서 내용을 볼 수 있으므로, 비밀번호등 민감정보를 포함하지 않도록 한다

// 클레임의 3가지 종류

// 클레임이란?
// - Payload에 담기는 정보 한 조각(key-value 쌍 하나하나)을 클레임이라 한다.
// - 토큰이 "이 사용자는 test디", "이 토큰은 10시에 만료된다" 같은 사실을 '주장(Claim)' 한다는 의미
// 서명이 유효하면 그 주장들을 신뢰할 수 있다


// 3.Signature

@Configuration
@EnableWebSecurity
public class SecurityConfig {

}
