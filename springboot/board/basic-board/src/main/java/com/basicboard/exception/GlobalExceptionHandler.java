package com.basicboard.exception;

import com.basicboard.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserIdException(DuplicateUserIdException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage())
                );
    }
}
