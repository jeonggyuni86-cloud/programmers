package com.board_practice.service;

import com.board_practice.domain.entity.Board;
import com.board_practice.domain.repository.BoardRepository;
import com.board_practice.dto.BoardDeleteRequestDto;
import com.board_practice.dto.BoardUpdateRequestDto;
import com.board_practice.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoards(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return boardRepository.findAll(pageable).getContent();
    }

    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    public Board getBoardDetail(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("[Board] 게시글을 찾을 수 없습니다. ID = " + id));
    }

    @Transactional
    public void saveBoard(
            String userId, String title,
            String content, MultipartFile file
    ) {
        String filePath = fileService.storeFile(file);

        Board board = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(
            long id,
            BoardUpdateRequestDto dto
    ) {
        Board board = boardRepository.findById(id)
                .orElseThrow(
                        () -> new BoardNotFoundException("[BOARD] 수정할 게시글이 없습니다. ID = " + id)
                );

        String filePath = board.getFilePath();
        if( dto.isFileFlag() ) {
            fileService.deleteFile(filePath);
            filePath = fileService.storeFile(dto.getFile());
        }

        board.update( dto.getTitle(), dto.getContent(), filePath);
        //트랜젝션이 끝날때 수정 사항을 자동으로 감지하여, DB로 업데이트 된다
    }

    @Transactional
    public void deleteBoard(long id, BoardDeleteRequestDto dto) {
        if( !boardRepository.existsById(id) ) {
            throw new BoardNotFoundException("[Board] 삭제할 게시글이 없습니다 ID = " + id);
        }
        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

}
