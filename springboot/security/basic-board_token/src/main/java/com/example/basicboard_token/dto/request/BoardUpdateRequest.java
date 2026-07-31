package com.example.basicboard_token.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record BoardUpdateRequest(
        String title,
        String content,
        MultipartFile file,
        boolean fileFlag
) {
}
