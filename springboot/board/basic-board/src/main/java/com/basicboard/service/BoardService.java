package com.basicboard.service;

import com.basicboard.domain.entity.Board;
import com.basicboard.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getBoards(int page, int size) {
        var pageable = PageRequest.of(page - 1, size, Sort.by("id").descending()); // 인덱스는 0 부터 시작하기 때문에
        // * findAll(pageable).getContent() 의 getContent() 란?
        // findAll(pageable) 의 반환 타입은 Page<Board>
        // Page가 제공하는 것들
        // - getContent() -> List<Board> "이번 게이지의 게시글 목록" -> 예) 1페이지 10개 항목
        // - getTotalElements() -> Long : 전체 게시글 수
        // - getTotalPages() -> int : 전체 페이지 수
        // - isLast() -> boolean : 마지막 페이지 여부

        // 주의 : getContent() : 'Content' 는 Board 엔티티의 Content가 아님
        return boardRepository.findAll(pageable).getContent();
    }

}
