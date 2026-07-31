package com.example.basicboard_token.dto.response;

import com.example.basicboard_token.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        long id,
        String userId,
        String content,
        @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
        LocalDateTime createdAt
) {
}
