package com.example.oauth2_basic_board.dto.response;

import com.example.basicboard_token.domain.entity.Member;
import com.example.basicboard_token.domain.entity.Role;

public record MemberResponse(long id, String userId, String userName, Role role) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUserId(),
                member.getUserName(),
                member.getRole()
        );
    }
}
