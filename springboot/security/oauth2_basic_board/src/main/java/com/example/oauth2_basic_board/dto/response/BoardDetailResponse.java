package com.example.oauth2_basic_board.dto.response;

import com.example.oauth2_basic_board.constant.DateTimeFormatConstant;
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
