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

    public Optional<Member> login(LoginRequestDto dto) {
        return memberRepository.findByUserId(dto.getUsername())
                .filter(
                        member -> member.getPassword().equals(dto.getPassword())
                );

    }

}
