package com.basicboard.service;

import com.basicboard.domain.entity.Member;
import com.basicboard.domain.repository.MemberRepository;
import com.basicboard.dto.LoginRequestDto;
import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.exception.DuplicateUserIdException;
import com.basicboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// * @Slf4j 로 서비스 레벨 로깅하기(lombok)
// lombok을 안쓰면 ...
// private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MemberService.class);

// 로그 레벨 5단계 - "이 메시지가 얼마나 중요한가"의 등급
// trace < debug < info < warn < error
// - trace/debug : 개발 중 흐름 추적용 (운영 기본 설정에선 안 찍힘)
// - info : 의미 있는 비즈니스 이벤트(가입 완료, 글 등록 등 "정상인데 기록할 가치가 있는 일")
// - warn : 이상하지만 서비스는 계속되는 상황(로그인 실패, 중복 가입 시도 등)
// - error : 예상 못한 실패 (500 계열)

// {} 플레이스 홀더 - 문자열 + 값 대신 이걸 쓴다
// log.debug("가입 요청 : " + dto.getUserId()); // (X) - 렙벨이 꺼져 있어도 문자열 연결 비용은 발생
// log.debug("가입 요청 : " userId= ()", dto.getUserId()); // (O) - 실제로 찍힐 때만 조립(지연 평가)

// 민감한 정보는 로그에 남기지 않는다.
// - 객체를 통째로 찍으면 그안의 값들이 그대로 로그에 남는다.
// - 로그 파일은 오래 보관되고, 여러 사람들이 보므로, 비밀번호 / 토큰 / 주민번호 등은 제외해야 한다.

// AOP와의 역할 분담
// - AOP : "무엇이 호출 됐고 몇 ms가 걸렸나 (공통적인 기계적 관심사)
// - 서비스 로그 : "비즈니스에 무슨 일이 있었나" (선별적의미 - 분기점, 상태 변화, 실패 지점만)
// -> 그래서 서비스에서는 "메서드 시작/끝" 로그를 찍지 않는다.

@Slf4j
@Service
// 이 클래스의 "모든 메서드"에 기본 적용한다.
// - readOnly = true의 효과
// - 이 트랜젝션은 데이터를 안 바꾼다. - JPA에게 알려주는 효과 -> 최적화ㅈ
// 하이버네이트가 변경감지를 위한 스냅샷을 안 만들어 메모리 / 성능에 유리
// insert / update / delete가 필요한 메서드는 @Transactional를 다시 붙인다
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        // dto -> Member로 변환해줄 매퍼 필요

        if(memberRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 ID 입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }

    // * Optional<Member> : NPE 예방을 위해 사용
    // - 예전에는 "값이 없음"을 null로 표햔했는데, null을 깜빡하고 그냥쓰면. 실행중에 NPE 발생
    // 예) Member m = findByUserId("test"); m.getUserName(); -> m이 null이면 NPE 발생
    // - 게다가 반환 타입만 봐서는 "null"이 올 수 있는지 알 수가 없어 실수하기 쉬웠다.

    // Optional : "값이 없을 수도 있다" 를 타입으로 알려주는 상자(Wrapper)
    // - 반환 타입이 Optional이면 "값이 없을 수 있으니 처리해라" 라고 컴파일 단계에서 강제한다.
    // - 즉 "없을 수 있음"을 문서가 아니라 "타입"으로 표현해 실수를 막는 장치이다.

    // Wrapper를 여는 (값을 꺼내는) 주요 메서드
    // 1. isPresent() / isEmpty() : 값이 있는지 boolean 타입으로 반환
    // 2. get() : 값을 꺼냄 (비어있으면 예외 발생) -> 되도록 쓰지 않는다.
    // 3. orElse('기본값') : 있으면 값을 가져오고 없으면 기본값을 가져옴 (기본 값은 미리 계산됨)
    // 4. orElseGet('함수') : 있으면 값을 가져오고 없으면 해당 함수를 실행한다 (없을 때만 계산)
    // 5. map(함수) : 값이 있으면 다른 값으로 변환, 없으면 empty
    // 6. filter(조건) : 값이 있고 조건을 만족하면 유지, 아니면 empty

    // 세 가지 상태
    // 1. Optional.of("값") : 값이 확실히 있을 때 (값이 null이면 즉시 예외)
    // 2. Optional.empty() : 값이 없음
    // 3. Optional.ofNullable("값") : 값이 null 일 수도 있을 때 (null이면 empty, 아니면 of)

    // 주의 (자주 하는 실수)
    //   - get() 남발 금지: 비어있으면 예외다. orElse* 계열로 안전하게 꺼낸다
    //   - if(opt.isPresent()) opt.get() 패턴은 사실상 null 체크와 다를 게 없다 -> map/orElseGet 으로 흘려보내자
    //   - 보통 "반환 타입"에만 쓴다. 필드나 파라미터 타입으로는 잘 쓰지 않는다 (설계 관례)

    public Optional<Member> login(LoginRequestDto dto) {
        // return 한 줄에 쓰인 람다식과 Optional.filter 이해하기
        // ---------------------------------------------------------------------------------
        // # findByUserId 는 Optional<Member> 를 돌려준다 (회원이 있을 수도, 없을 수도)
        // # .filter( member -> 조건 ) 의 동작:
        //     - Optional 안에 값이 "있고" + 람다가 true  -> 그 값을 그대로 유지
        //     - 값이 "없거나"          + 람다가 false -> 빈 Optional(Optional.empty)로 만든다
        //   => "아이디로 찾은 회원이 있고, 비밀번호까지 일치하면 남기고, 아니면 비운다" = 로그인 성공/실패 판정

        // # member -> member.getPassword().equals(...) 가 바로 람다식(이름 없는 함수)이다
        //     member                         : 입력 파라미터 (Optional 안에 든 Member)
        //     ->                             : "이것을 받아서 ~를 반환한다"
        //     member.getPassword().equals(..): 반환값 (boolean). 비밀번호가 같으면 true

        // # 만약 람다(와 Optional)를 쓰지 않았다면, 아래처럼 풀어 쓴 것과 같다:
        //
        //     // 1) 아이디로 회원을 조회한다 (없으면 null 이 나오도록 orElse(null) 사용)
        //     Member member = memberRepository.findByUserId(request.getUsername()).orElse(null);
        //
        //     // 2) 회원이 존재하고(null 아님) + 비밀번호가 일치하면 -> 로그인 성공
        //     if (member != null && member.getPassword().equals(request.getPassword())) {
        //         return Optional.of(member);   // 성공: 회원을 담아 반환
        //     }
        //
        //     // 3) 아이디가 없거나 비밀번호가 틀리면 -> 로그인 실패
        //     return Optional.empty();          // 실패: 빈 Optional 반환
        //
        //   => 위 if 분기(널 체크 + 비밀번호 비교)를 .filter(람다) 한 줄로 압축한 것이 아래 코드다
        return memberRepository.findByUserId(dto.getUsername())
                .filter(member -> member.getPassword().equals(dto.getPassword()));

    }

}
