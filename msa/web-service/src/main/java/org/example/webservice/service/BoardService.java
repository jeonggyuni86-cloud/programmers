package org.example.webservice.service;

import lombok.RequiredArgsConstructor;
import org.example.webservice.client.BoardClient;
import org.example.webservice.dto.BoardPageResponseDto;
import org.example.webservice.dto.BoardSearchRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardClient boardClient;

    public BoardPageResponseDto searchBoard(
            String authorization,
            BoardSearchRequestDto condition,
            int page,
            int size
    ) {
        return boardClient.searchBoards(authorization, condition, page, size);
    }
}
