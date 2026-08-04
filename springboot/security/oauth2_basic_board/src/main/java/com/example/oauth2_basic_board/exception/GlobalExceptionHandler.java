package com.example.oauth2_basic_board.exception;

import com.example.oauth2_basic_board.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception e) {
        log.warn("401 응답 : 로그인 실패");
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorResponse(
                        UNAUTHORIZED.value(),
                        "아이디 또는 비밀번호가 올바르지 않습니다."
                ));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {
        log.warn("400 응답 : {}", e.getMessage());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(
                        BAD_REQUEST.value(),
                        "요청 내용을 확인해 주세요."
                ));
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserIdException(DuplicateUserIdException e) {
        log.warn("409 응답 : {}", e.getMessage());
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        new ErrorResponse(CONFLICT.value(), e.getMessage())
                );
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFoundException(BoardNotFoundException e) {
        log.warn("404 응답 : {}", e.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        new ErrorResponse(NOT_FOUND.value(), e.getMessage())
                );
    }

    @ExceptionHandler(BoardAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleBoardAccessDeniedException(
            BoardAccessDeniedException e
    ) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(
                        new ErrorResponse(
                                FORBIDDEN.value(), e.getMessage())
                );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("500 응답 : (예상치 못한 예외 발생)", e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다")
                );
    }
}
