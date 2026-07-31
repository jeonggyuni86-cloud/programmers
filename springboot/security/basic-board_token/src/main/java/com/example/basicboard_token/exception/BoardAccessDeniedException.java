package com.example.basicboard_token.exception;

public class BoardAccessDeniedException extends RuntimeException {
    public BoardAccessDeniedException(String message) {
        super(message);
    }
}
