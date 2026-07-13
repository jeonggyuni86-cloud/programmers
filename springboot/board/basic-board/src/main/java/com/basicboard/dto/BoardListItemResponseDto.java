package com.basicboard.dto;

import com.basicboard.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListItemResponseDto {
    @Schema(description = "게시글 id", example = "1")
    private long id;

    @Schema(description = "제목", example = "첫 번째 게시글")
    private String title;

    @Schema(description = "사용자 아이디", example = "hong")
    private String userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "댓글 수", example = "3")
    private long commentCount;

    @Schema(description = "작성일시", example = "2026-06-01 09:17")
    @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
    private LocalDateTime created;

}
