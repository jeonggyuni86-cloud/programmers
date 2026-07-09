package com.basicboard.service;

import com.basicboard.domain.entity.Member;
import com.basicboard.domain.repository.MemberRepository;
import com.basicboard.dto.LoginRequestDto;
import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.exception.DuplicateUserIdException;
import com.basicboard.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

// * 순수 단위 테스트 - 서비스 로직만 검증한다

// * Mockito란?
// - "가짜 객체(Mock)"를 쉽게 만들어 주는 자바 테스트 라이브러리다.
// - Mock -> 진짜와 유사한 모양의 빈 껍데기 -> 시나리오를 심어줄 수 있다.
// - SpringBoot-startup에 내장되어있다

// * 가짜가 필요한 이유
// - 단위 테스트 "대상 하나(MemberService)"가 제대로 동작하는지만 보고 싶음
// - 그런데, MemberService는 MemberRepository에 의존한다.
// -> 진짜 레포지토리를 쓰면 (1) DB가 떠있어야 하고 (2) 느리다 (3) DB / 레포지토리 버그까지 섞여
// "무엇이 틀렸는지" 불분명해진다.
// - 그래서 레포지토리를 "가짜"로 바꿔, 그 행동을 내가 정해놓고 -> 순수하게 서비스 로직만 검증한다.

// * 자주 쓰는 Mockito 문법
// @ExtendWith(MockitoExtension.class) : 이 테스트에서 Mockito 기능을 켠다
// @Mock : 가짜 객체를 만들어준다.
// @InjectMocks : 테스트 대상을 만들고 위 @Mock을 주입한다.

// * 스터빙 : "이렇게 부르면 이 값을 돌려줘라"
// - given(repo.existsByUserId("newbie")).willReturn(false)
// 특정 인자를 주면 false를 반환하라고 시나리오 준 상황
// -given(memberRepository.findByUserId("test")).willReturn(Optional.of(member));
// test를 주면 Optional.of(Member)를 반환해라
// - given(repo.count()).willThrow(new RuntimeException())
// 호출되면 예외를 던지게 시나리오 작성

// * 검증 - "그 메서드가 호출됐는지" (verify)  : 주로 반환값 없는 void 로직 확인에 쓴다
// - verify(repo).save(entity);              // save 가 "그 엔티티로" 정확히 1번 호출됐어야 한다 (기본=1번)
// - verify(repo, times(2)).save(any());     // 정확히 2번
// - verify(repo, never()).save(any());      // 한 번도 호출되면 안 된다

// * 인자 매처 - "구체값 대신 '아무거나' 로 느슨하게" (any, eq ...)
// - any()          : 아무 값이나 (타입 무관)         예) verify(repo).save(any());
// - anyString()    : 아무 문자열이나
// - eq("hong")     : 정확히 "hong" 인 인자
// - 주의: 한 메서드의 인자 중 하나라도 매처(any 등)를 쓰면, 나머지 인자도 전부 매처로 써야 한다
// 예) verify(repo).method(eq("hong"), any());   // "hong" 은 그냥 값이 아니라 eq() 로 감싼다

// # 핵심 아이디어: MemberService 만 진짜 객체로 쓰고, 그것이 의존하는 것들(리포지토리/매퍼)은 "가짜(Mock)"로 바꾼다
//   - 진짜 DB 리포지토리를 쓰면 DB 가 떠 있어야 하고 느리다. 우리는 "서비스 로직" 만 보고 싶다
//   - 그래서 리포지토리를 Mock 으로 두고 "이 메서드는 이런 값을 돌려준다고 치자" 라고 우리가 지정한다
//   - 스프링을 아예 띄우지 않으므로 매우 빠르다 (@SpringBootTest 없음에 주목)

// # 참고: 서비스에 붙은 클래스 레벨 @Transactional 은 여기선 동작하지 않는다
//         (트랜잭션은 스프링이 프록시로 걸어주는 기능인데, 지금은 스프링을 안 띄우고 new 로 만든 순수 객체라서)

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository; // 가짜 레포지토리

    @Mock
    private MemberMapper memberMapper; // 가짜 매퍼

    @InjectMocks
    private MemberService memberService; // 테스트대상 (위 두 Mock이 주입된다)

    @Test
    @DisplayName("로그인 - 아이디가 있고, 비밀번호가 일치하면 회원을 담은 Optional 반환")
    void login_아이디와_비밀번호가_맞으면_회원을_반환한다() {
        // given - "test/1234" 회원이 있다고 가정
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();
        given(memberRepository.findByUserId("test"))
                .willReturn(Optional.of(member));

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("test");
        requestDto.setPassword("1234");

        Optional<Member> result = memberService.login(requestDto);
        assertThat(result).isPresent();
        assertThat(result.get().getUserName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("로그인 - 비밀번호가 틀리면 빈 Optional을 반환한다")
    void login_비밀번호가_틀리면_빈_Optional() {
        // given - "test/1234" 회원이 있다고 가정
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();
        given(memberRepository.findByUserId("test"))
                .willReturn(Optional.of(member));

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("test");
        requestDto.setPassword("9999");

        Optional<Member> result = memberService.login(requestDto);
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("로그인 - 비밀번호가 틀리면 빈 Optional을 반환한다")
    void login_ID가_없으면_빈_Optional() {
        //given
        given(memberRepository.findByUserId("nobody")).willReturn(Optional.empty());

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("nobody");
        requestDto.setPassword("9999");

        Optional<Member> result = memberService.login(requestDto);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("회원가입 - 아이디가 중복이 아니면 회원을 지정한다")
    void join_중복이_아니면_저장한다() {
        // given
        MemberJoinRequestDto dto = new MemberJoinRequestDto();
        dto.setUserId("test");
        dto.setPassword("1234");
        dto.setUserName("홍길동");

        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();
        given(memberRepository.existsByUserId("test")).willReturn(false);
        given(memberMapper.toEntity(dto)).willReturn(member);

        // when
        memberService.join(dto);

        // then
        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("회원가입 - 아이디가 이미 있으면 DuplicatedUserIdException을 던지고 저장하지 않는다")
    void join_중복이면_예외() {
        // given
        MemberJoinRequestDto dto = new MemberJoinRequestDto();
        dto.setUserId("test");
        dto.setPassword("1234");
        dto.setUserName("홍길동");

        given(memberRepository.existsByUserId("test")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> memberService.join(dto))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessageContaining("[회원가입] 이미 존재하는 ID 입니다.");
        verify(memberRepository, never()).save(any());
    }

}