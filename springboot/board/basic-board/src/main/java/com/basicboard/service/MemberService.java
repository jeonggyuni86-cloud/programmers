package com.basicboard.service;

import com.basicboard.domain.entity.Member;
import com.basicboard.domain.repository.MemberRepository;
import com.basicboard.dto.LoginRequestDto;
import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.exception.DuplicateUserIdException;
import com.basicboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Optional<Member> login(LoginRequestDto dto) {
        return memberRepository.findByUserId(dto.getUsername())
                .filter(
                        member -> member.getPassword().equals(dto.getPassword())
                );

    }

}
