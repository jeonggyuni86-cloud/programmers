package com.example.oauth2_basic_board.domain.repository;

import com.example.oauth2_basic_board.dto.request.BoardSearchRequest;
import com.example.oauth2_basic_board.dto.response.BoardAuthorStatResponse;
import com.example.oauth2_basic_board.dto.response.BoardListItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BoardRepositoryCustom {
    Page<BoardListItemResponse> searchBoard(
            BoardSearchRequest request,
            Pageable pageable
    );

    List<BoardAuthorStatResponse> countByAuthor(long minCount);
}
