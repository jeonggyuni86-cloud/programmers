package com.board_practice.dto;

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
    private String filePath;
}
