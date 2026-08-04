package com.example.oauth2_basic_board.dto.response;

import com.example.oauth2_basic_board.constant.DateTimeFormatConstant;
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
