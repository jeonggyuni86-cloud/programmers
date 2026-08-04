package com.example.oauth2_basic_board.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BoardListResponse(
        List<BoardDetailResponse> boards,
        boolean last,
        int totalPages
) {
}
