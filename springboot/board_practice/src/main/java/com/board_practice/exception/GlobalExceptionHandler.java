package com.board_practice.exception;

import com.board_practice.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserIdException(DuplicateUserIdException ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponseDto.builder()
                                .status(HttpStatus.CONFLICT.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
}
