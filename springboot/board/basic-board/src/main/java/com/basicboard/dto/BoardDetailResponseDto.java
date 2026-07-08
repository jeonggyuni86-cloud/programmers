package com.basicboard.dto;

import com.basicboard.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDetailResponseDto {
    private String title;
    private String content;

    @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
    private LocalDateTime created;

    private String userId;
    private String filePath;
}
