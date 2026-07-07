package com.board_practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.board_practice.constant.DateTimeFormatConstant.DATETIME_PATTERN;

@Getter
@Builder
public class BoardDetailResponseDto {
    private String title;
    private String content;

    @JsonFormat(pattern = DATETIME_PATTERN)
    private LocalDateTime created;

    private String userId;
    private String filePath;

}
