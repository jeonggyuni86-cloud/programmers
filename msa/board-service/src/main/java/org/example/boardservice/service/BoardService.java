package org.example.boardservice.service;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.domain.repository.BoardRepository;
import org.example.boardservice.domain.entity.Board;
import org.example.boardservice.dto.BoardDetailResponseDto;
import org.example.boardservice.dto.BoardListItemResponseDto;
import org.example.boardservice.dto.BoardPageResponseDto;
import org.example.boardservice.dto.BoardSearchRequestDto;
import org.example.boardservice.exception.BoardNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public BoardPageResponseDto searchBoards(
            BoardSearchRequestDto condition,
            Pageable pageable
    ) {
        Page<BoardListItemResponseDto> result = boardRepository
                .searchBoards(condition, pageable)
                .map(BoardListItemResponseDto::from);

        return BoardPageResponseDto.from(result);
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardWithComments(Long boardId) {
        Board board = boardRepository.findWithCommentsById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId));

        return BoardDetailResponseDto.from(board);
    }
}
