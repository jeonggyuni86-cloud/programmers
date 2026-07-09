package com.basicboard.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

// * @DataJpaTest
// - 기본적으로 내 DataSource 설정을 버리고, "기본 모드 임베디드 H2"로 갈아 끼운다.
// - JPA 레포지토리와 EntityManager 등 "데이터 계층"에 필요한 Bean만 로드한다 (컨트롤러/서비스는 안뜬다 -> 가볍다)
// - 각 테스트 들은 트랜젝션 안에서 돌고 "끝나면 자동 롤백" 된다 -> 테스트끼리 데이터가 안 섞인다.

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
}