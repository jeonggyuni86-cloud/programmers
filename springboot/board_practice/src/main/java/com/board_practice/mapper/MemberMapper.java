package com.board_practice.mapper;

import com.board_practice.domain.entity.Member;
import com.board_practice.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(MemberJoinRequestDto dto){
        return Member.builder()
                .userId(dto.userId())
                .password(dto.password())
                .userName(dto.userName())
                .build();
    }
}
