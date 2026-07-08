package com.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardWriteRequestDto {
    private String title;
    private String content;
    private String userId;

    // MultipartFile
    // MultiPartFormData로 업로드된 '파일 1개'를 스프링이 감싸서 넘겨주는 타입이다.
    // 파일의 바이트뿐 아니라 메타데이터도 함께 들고 있다. 자주 쓰는 메서드:
    //     getOriginalFilename() : 업로드된 원본 파일명 (예: "고양이.png")
    //     getContentType()      : MIME 타입 (예: "image/png")
    //     getSize()             : 파일 크기(byte)
    //     isEmpty()             : 파일을 안 골랐거나 빈 파일이면 true
    //     getInputStream()      : 내용을 읽는 스트림
    //     transferTo(dest)      : 실제 디스크 경로로 저장
    private MultipartFile file;
}
