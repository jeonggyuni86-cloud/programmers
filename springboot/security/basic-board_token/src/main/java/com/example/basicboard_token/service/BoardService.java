package com.example.basicboard_token.service;

import com.example.basicboard_token.config.security.CustomUserDetails;
import com.example.basicboard_token.domain.entity.Board;
import com.example.basicboard_token.domain.entity.Member;
import com.example.basicboard_token.domain.entity.Role;
import com.example.basicboard_token.dto.request.BoardSearchRequest;
import com.example.basicboard_token.dto.request.BoardUpdateRequest;
import com.example.basicboard_token.dto.request.BoardWriteRequest;
import com.example.basicboard_token.dto.response.BoardAuthorStatResponse;
import com.example.basicboard_token.dto.response.BoardDetailResponse;
import com.example.basicboard_token.dto.response.BoardListItemResponse;
import com.example.basicboard_token.dto.response.BoardListResponse;
import com.example.basicboard_token.exception.BoardAccessDeniedException;
import com.example.basicboard_token.mapper.BoardMapper;
import com.example.basicboard_token.service.component.BoardHandler;
import com.example.basicboard_token.service.component.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardHandler boardHandler;
    private final BoardMapper boardMapper;
    private final FileStorage fileStorage;

    // --------------- C ---------------
    @Transactional
    public void saveBoard(BoardWriteRequest request) {
        String filePath = fileStorage.store(request.file());
        Board board = boardMapper.toEntity(request, getLoginMember().getUserId(), filePath);
        boardHandler.save(board);
    }

    // --------------- R ---------------
    public BoardDetailResponse getBoardDetail(long id) {
        Board board = boardHandler.getBoard(id);
        return boardMapper.toBoardDetailResponse(board);
    }

    public BoardListResponse getBoards(int page, int size) {

        Pageable pageable =
                PageRequest.of(
                        page - 1,
                        size,
                        Sort.by("id").descending()
                );

        Page<Board> result =
                boardHandler.getBoards(pageable);

        return BoardListResponse.builder()
                .boards(
                        result.getContent()
                                .stream()
                                .map(boardMapper::toBoardDetailResponse)
                                .toList()
                )
                .last(result.isLast())
                .totalPages(result.getTotalPages())
                .build();
    }

    public long getTotalBoards() {
        return boardHandler.getTotalBoards();
    }

    public Page<BoardListItemResponse> searchBoards(
            BoardSearchRequest request,
            Pageable pageable
    ) {
        return boardHandler.searchBoards(request, pageable);
    }

    public Resource downloadFile(String fileName) {
        return fileStorage.download(fileName);
    }

    public List<BoardAuthorStatResponse> getAuthorsStats(long minCount) {
        return boardHandler.countByAuthor(minCount);
    }


    // --------------- U ---------------
    @Transactional
    public void updateBoard(
            long id,
            BoardUpdateRequest request
    ) {
        Board board = boardHandler.getBoard(id);
        requireOwnerOrAdmin(board);

        String filePath = board.getFilePath();
        if(request.fileFlag()) {
            fileStorage.delete(filePath);
            filePath = fileStorage.store(request.file());
        }

        board.update(
                request.title(),
                request.content(),
                filePath
        );
    }

    // --------------- D ---------------
    @Transactional
    public void deleteBoard(
            long id
    ) {
        Board board = boardHandler.getBoard(id);

        requireOwnerOrAdmin(board);

        boardHandler.delete(board);
        fileStorage.delete(board.getFilePath());
    }


    private Member getLoginMember() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails principal =
                (CustomUserDetails) authentication.getPrincipal();

        return principal.getUser();
    }

    private void requireOwnerOrAdmin(Board board) {
        Member member = getLoginMember();
        boolean isAdmin = member.getRole() == Role.ROLE_ADMIN;
        boolean isOwner = board.getUserId().equals(member.getUserId());
        if (!isAdmin && !isOwner) {
            throw new BoardAccessDeniedException("게시글 수정/삭제 권한이 없습니다.");
        }
    }

}
