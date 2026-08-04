package com.example.oauth2_basic_board.service.component;

import com.example.oauth2_basic_board.domain.entity.Board;
import com.example.oauth2_basic_board.domain.repository.BoardRepository;
import com.example.oauth2_basic_board.dto.request.BoardSearchRequest;
import com.example.oauth2_basic_board.dto.response.BoardAuthorStatResponse;
import com.example.oauth2_basic_board.dto.response.BoardListItemResponse;
import com.example.oauth2_basic_board.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardHandler {
    private final BoardRepository boardRepository;

    public Board getBoard(Long id) {
        return boardRepository.findDetailById(id)
                .orElseThrow(() -> new BoardNotFoundException("[Board] 게시글을 찾을 수 없습니다. ID = " + id));
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public long getTotalBoards() {
        return boardRepository.count();
    }

    public List<BoardAuthorStatResponse> countByAuthor(long minCount) {
        return boardRepository.countByAuthor(minCount);
    }

    public Page<BoardListItemResponse> searchBoards(
            BoardSearchRequest boardSearchRequest,
            Pageable pageable
    ) {
        return boardRepository.searchBoard(boardSearchRequest, pageable);
    }

    public void save(Board board) {
        boardRepository.save(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

}
