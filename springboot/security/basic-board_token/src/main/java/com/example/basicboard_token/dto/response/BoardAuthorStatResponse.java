package com.example.basicboard_token.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record BoardAuthorStatResponse(
        String userId,
        String userName,
        long boardCount
) {
    @QueryProjection
    public BoardAuthorStatResponse(String userId, String userName, long boardCount) {
        this.userId = userId;
        this.userName = userName;
        this.boardCount = boardCount;
    }
}
