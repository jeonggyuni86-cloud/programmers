package com.example.basicboard_token.dto.request;

public record MemberJoinRequest(
        String userId,
        String password,
        String userName
) {

}
