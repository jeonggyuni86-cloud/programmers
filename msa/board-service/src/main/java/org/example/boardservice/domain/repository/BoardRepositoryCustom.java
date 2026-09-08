package org.example.boardservice.domain.repository;

import org.example.boardservice.dto.BoardSearchRequestDto;
import org.example.boardservice.dto.BoardSearchRowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardSearchRowDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable);
}
