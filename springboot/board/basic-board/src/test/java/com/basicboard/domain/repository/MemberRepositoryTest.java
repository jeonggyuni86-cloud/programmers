package com.basicboard.domain.repository;

import com.basicboard.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// * @DataJpaTest
// - 기본적으로 내 DataSource 설정을 버리고, "기본 모드 임베디드 H2"로 갈아 끼운다.
// - JPA 레포지토리와 EntityManager 등 "데이터 계층"에 필요한 Bean만 로드한다 (컨트롤러/서비스는 안뜬다 -> 가볍다)
// - 각 테스트 들은 트랜젝션 안에서 돌고 "끝나면 자동 롤백" 된다 -> 테스트끼리 데이터가 안 섞인다.

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("existById - 존재하는 ID면 true 반환")
    void existsByUserId_존재하면_true() {
        boolean exists = memberRepository.existsByUserId("test");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existById - 존재하지 않는 ID면 false를 반환")
    void existsByUserId_없으면_false() {
        boolean exists = memberRepository.existsByUserId("nobody");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("findByUserId - 존재하는 아이디로 조회하면 회원이 담긴 Optional을 반환한다")
    void findByUserId_존재하면_회원() {
        Optional<Member> found = memberRepository.findByUserId("test");

        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("홍길동");
    }
    @Test
    @DisplayName("findByUserId - 없는 아이디로 조회하면 빈 Optional을 반환한다")
    void findByUserId_없으면_빈_Optional() {
        Optional<Member> found = memberRepository.findByUserId("nobody");

        assertThat(found).isEmpty();
    }

}