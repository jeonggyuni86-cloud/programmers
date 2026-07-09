package com.board_practice.domain.repository;

import com.board_practice.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;             // 부트4 경로
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;    // 부트4 경로

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내가 지정한 H2 사용
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.builder()
                .userId("hong").password("1234").userName("홍길동").build());
    }

    @Test
    void existsByUserId_존재하면_true() {
        assertThat(memberRepository.existsByUserId("hong")).isTrue();
        assertThat(memberRepository.existsByUserId("nobody")).isFalse();
    }

    @Test
    void findByUserId_있으면_회원() {
        assertThat(memberRepository.findByUserId("hong")).isPresent();
        assertThat(memberRepository.findByUserId("nobody")).isEmpty();
    }
}