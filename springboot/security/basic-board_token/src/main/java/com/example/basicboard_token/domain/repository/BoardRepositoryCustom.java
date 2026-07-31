package com.example.basicboard_token.domain.repository;

import com.example.basicboard_token.dto.request.BoardSearchRequest;
import com.example.basicboard_token.dto.response.BoardAuthorStatResponse;
import com.example.basicboard_token.dto.response.BoardListItemResponse;
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
