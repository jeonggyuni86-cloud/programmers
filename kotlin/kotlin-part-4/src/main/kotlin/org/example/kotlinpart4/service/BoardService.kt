package org.example.kotlinpart4.service

import org.example.kotlinpart4.domain.entity.Board
import org.example.kotlinpart4.domain.repository.BoardRepository
import org.example.kotlinpart4.dto.BoardCreateRequest
import org.example.kotlinpart4.dto.BoardUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoardService(
    private val repository: BoardRepository
) {
    fun getBoards(
        page: Int,
        size: Int,
        keyword: String? = null
    ): Page<Board> {
        val pageable: Pageable = PageRequest.of(page - 1, size, Sort.by("id").descending())

        return if (keyword.isNullOrBlank()) {
            repository.findAll(pageable)
        } else {
            repository.findByTitleContaining(keyword, pageable)
        }
    }

    @Transactional
    fun createBoard(request: BoardCreateRequest) =
        repository.save(request.toEntity())

    fun getBoard(id: Long): Board? =
        repository.findByIdOrNull(id)

    // 트랜젝션 안에서 조회한 엔티티는 영속 상태다.
    // 값을 바꾸면 트랜젝션이 끝날 때 JPA가 변경을 감지해, UPDATE SQL을 자동으로 날린다. (dirty checking)
    @Transactional
    fun updateBoard(id: Long, request: BoardUpdateRequest) {
        // ?: -> 엘비스 연산자 ?: [null이면 수행]
        val board = repository.findByIdOrNull(id) ?: return
        board.update(request.title, request.content)
    }

    @Transactional
    fun deleteBoard(id: Long) =
        repository.deleteById(id)
}