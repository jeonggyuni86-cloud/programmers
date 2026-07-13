package com.example.board_refactor.dto.response;


import com.example.board_refactor.constant.DateTimeFormatConstant;
import com.example.board_refactor.domain.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardResponseDto(
        Long id,
        String title,
        String content,
        String userId,
        String filePath,
        @JsonFormat(pattern = DateTimeFormatConstant.DATETIME_PATTERN)
        LocalDateTime created
) {
   public static BoardResponseDto from(Board board) {
       return BoardResponseDto.builder()
               .id(board.getId())
               .title(board.getTitle())
               .content(board.getContent())
               .userId(board.getUserId())
               .filePath(board.getFilePath())
               .created(board.getCreated())
               .build();
   }
}
