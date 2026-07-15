package com.basicboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BoardAuthorStatResponseDto {

    private final String userId;
    private final String userName;
    private final long boardCount;

    @QueryProjection
    public BoardAuthorStatResponseDto(String userId, String userName, long boardCount) {
        this.userId = userId;
        this.userName = userName;
        this.boardCount = boardCount;
    }
}
