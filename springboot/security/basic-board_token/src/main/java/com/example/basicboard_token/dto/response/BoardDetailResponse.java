package com.example.basicboard_token.dto.response;

import com.example.basicboard_token.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BoardDetailResponse(
        long id,
        String title,
        String content,
        List<CommentResponse> comments,
        @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
        LocalDateTime createdAt,
        String userId,
        String filePath
) {
}
