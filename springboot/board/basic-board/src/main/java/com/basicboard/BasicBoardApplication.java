package com.basicboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// * RESTful : (REST, Representational State Transfer)
// 자원을 URI로 표현하고, HTTP 메서드로 그 자원에 대한 행위를 표현하는 API 설계원칙
// controller의 매핑을 아래 규칙에 맞게 설계하면 RESTful 하다고 한다.

// * 핵심원칙
// - 자원 중심 URI : URI는 명사(자원)로, 동사를 쓰지 않는다.


@SpringBootApplication
public class BasicBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicBoardApplication.class, args);
    }

}
