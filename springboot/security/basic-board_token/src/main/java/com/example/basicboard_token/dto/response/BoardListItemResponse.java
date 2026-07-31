package com.example.basicboard_token.dto.response;

import com.example.basicboard_token.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardListItemResponse(
        long id,
        String title,
        String userId,
        String userName,
        long commentCount,
        @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
        LocalDateTime createdAt
) {
}
