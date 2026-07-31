package com.example.basicboard_token.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record BoardWriteRequest(
        String title,
        String content,
        String userId,
        MultipartFile file
) {
}
