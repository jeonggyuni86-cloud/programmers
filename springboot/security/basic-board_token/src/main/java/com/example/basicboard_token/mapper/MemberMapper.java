package com.example.basicboard_token.mapper;

import com.example.basicboard_token.domain.entity.Member;
import com.example.basicboard_token.dto.request.MemberJoinRequest;
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
