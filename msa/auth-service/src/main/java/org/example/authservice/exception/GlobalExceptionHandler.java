package org.example.authservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserIdException(DuplicateUserIdException e) {
        log.warn("409 응답 : {}", e.getMessage());
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        new ErrorResponseDto(CONFLICT.value(), e.getMessage())
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
        log.error("500 응답 : (예상치 못한 예외 발생)", e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto(INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다")
                );
    }
}
