package com.board_practice.dto;

import com.board_practice.domain.entity.Board;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardListResponseDto(
        List<Board> boards,
        boolean last,
        int totalPages
) {
}
