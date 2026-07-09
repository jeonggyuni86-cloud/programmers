package com.board_practice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final boolean success;
    private final String url;
    private final String message;

    @Builder
    private LoginResponseDto(boolean success, String url, String message) {
        this.success = success;
        this.url = url;
        this.message = message;
    }

    private static LoginResponseDto of(
            boolean success,
            String url,
            String message
    ) {
        return new LoginResponseDto(success, url, message);
    }

    public static LoginResponseDto success(String message) {
        return of(true, "/", message);
    }
    public static LoginResponseDto fail(String message) {
        return of(false, null, message);
    }

}
