package com.example.oauth2_basic_board.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record BoardWriteRequest(
        String title,
        String content,
        String userId,
        MultipartFile file
) {
}
