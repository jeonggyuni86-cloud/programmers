package com.example.board_refactor.dto.response;

import com.example.board_refactor.constant.PathConstant;
import lombok.Builder;

@Builder
public record MemberLoginResponseDto (
        boolean success,
        String url,
        String message
) {
    public static MemberLoginResponseDto ok() {
        return of(true, PathConstant.HOME, "로그인에 성공했습니다.");
    }
    public static MemberLoginResponseDto fail() {
        return of(false, null, "아이디 또는 비밀번호가 일치하지 않습니다");
    }
    public static MemberLoginResponseDto of(
            boolean success,
            String url,
            String message
    ) {
        return MemberLoginResponseDto.builder()
                .success(success)
                .url(url)
                .message(message)
                .build();
    }
}