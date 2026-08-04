package com.example.oauth2_basic_board.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record BoardUpdateRequest(
        String title,
        String content,
        MultipartFile file,
        boolean fileFlag
) {
}
