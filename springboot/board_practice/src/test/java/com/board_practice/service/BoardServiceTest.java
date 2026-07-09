package com.board_practice.service;

import com.board_practice.domain.entity.Member;
import com.board_practice.domain.repository.MemberRepository;
import com.board_practice.dto.LoginRequestDto;
import com.board_practice.dto.MemberJoinRequestDto;
import com.board_practice.exception.DuplicateUserIdException;
import com.board_practice.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository; // 가짜 (DB 안 씀)
    @Mock
    MemberMapper memberMapper;
    @InjectMocks
    MemberService memberService; // 대상 (위 Mock들이 주입됨)

    @Test
    void login_성공() {
        Member member = Member.builder().userId("hong").password("1234").userName("홍길동").build();
        // (1) member 객체를 만든 것과 (2) 가짜가 그걸 돌려주게 하는 건 별개다!
        given(memberRepository.findByUserId("hong")).willReturn(Optional.of(member));

        LoginRequestDto req = new LoginRequestDto();
        req.setUsername("hong"); req.setPassword("1234");

        Optional<Member> result = memberService.login(req);
        assertThat(result).isPresent();
    }

    @Test
    void join_중복이면_예외() {
        MemberJoinRequestDto req = new MemberJoinRequestDto();
        req.setUserId("hong");
        given(memberRepository.existsByUserId("hong")).willReturn(true);

        assertThatThrownBy(() -> memberService.join(req))
                .isInstanceOf(DuplicateUserIdException.class);
        verify(memberRepository, never()).save(any()); // 중복이면 저장 안 함
    }
}