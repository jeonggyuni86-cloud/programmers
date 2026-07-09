package com.board_practice.service;

import com.board_practice.domain.entity.Member;
import com.board_practice.domain.repository.MemberRepository;
import com.board_practice.dto.LoginRequestDto;
import com.board_practice.dto.MemberJoinRequestDto;
import com.board_practice.exception.DuplicateUserIdException;
import com.board_practice.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        if(memberRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 ID 입니다");
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
