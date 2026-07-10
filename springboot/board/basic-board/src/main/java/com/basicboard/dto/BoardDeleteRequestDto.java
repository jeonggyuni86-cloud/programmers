package com.basicboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @RequestBody
// Controller에서 @RequestBody로 받으면 JSON -> Jackson 라이브러리가 이 객체로 바꾼다 (역 직렬화)
// 즉 JSON -> 객체
// - Jackson -> Setter로 값을 채운다.

@Getter
@Setter
@NoArgsConstructor
public class BoardDeleteRequestDto {
    @Schema(description = "함께 삭제할 첨부파일 경로(없으면 비움)", example = "3f2a1b_이력서.pdf")
    private String filePath;
}
