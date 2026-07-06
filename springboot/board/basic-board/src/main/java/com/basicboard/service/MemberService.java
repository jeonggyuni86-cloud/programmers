package com.basicboard.service;

import com.basicboard.domain.repository.MemberRepository;
import com.basicboard.dto.MemberJoinRequestDto;
import com.basicboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void join(MemberJoinRequestDto dto) {
        // dto -> Member로 변환해줄 매퍼 필요

        if(memberRepository.existsByUserId(dto.getUserId())) {
            // TODO : 예외 공통화 처리
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }

}
