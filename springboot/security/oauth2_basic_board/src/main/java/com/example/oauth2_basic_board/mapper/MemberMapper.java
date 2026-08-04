package com.example.oauth2_basic_board.mapper;

import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.dto.request.MemberJoinRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberJoinRequest request, String encodedPassword) {
        return Member.createUser(
                request.userId(),
                encodedPassword,
                request.userName()
        );
    }
}
