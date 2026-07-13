package com.example.board_refactor.dto.response;

import java.util.List;

public record BoardListResponseDto (
        List<BoardResponseDto> boards,
        boolean last,
        int totalPages
) { }
