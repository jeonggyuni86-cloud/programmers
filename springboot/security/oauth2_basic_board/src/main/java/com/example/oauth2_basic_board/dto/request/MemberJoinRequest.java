package com.example.oauth2_basic_board.dto.request;

public record MemberJoinRequest(
        String userId,
        String password,
        String userName
) {

}
