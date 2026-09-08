package org.example.boardservice.exception;

public class BoardAccessDeniedException extends RuntimeException {

    public BoardAccessDeniedException(String message) {
        super(message);
    }
}
