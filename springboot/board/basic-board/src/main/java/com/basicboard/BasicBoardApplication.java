package com.basicboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// * RESTful : (REST, Representational State Transfer)
// 자원을 URI로 표현하고, HTTP 메서드로 그 자원에 대한 행위를 표현하는 API 설계원칙
// controller의 매핑을 아래 규칙에 맞게 설계하면 RESTful 하다고 한다.

// * 핵심원칙
// - 자원 중심 URI : URI는 명사(자원)로, 동사를 쓰지 않는다. (ex. /boards (O), /getBoard (X))
// - HTTP 메서드로 행위 구분
// - GET : 조회 (예: GET /boards (복수형), GET /boards/1(id) (단수))
// - POST : 생성 (예: POST /boards)
// - PUT : 전체 수정 (예: PUT /boards/1 (1번내용전체 수정))
// - PATCH : 부분 수정 (예: PATCH /boards/1 (1번 내용 부분수정))
// - DELETE : 삭제 (예: DELETE /boards/1 (1번 전체삭제))

// * 영속성(Persistence) - 관계 : ORM(개념) > JPA(표준)


@SpringBootApplication
public class BasicBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicBoardApplication.class, args);
    }

}
