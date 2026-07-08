package com.basicboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // getBytes() + Files.writ() 빙식과 비겨 했을 때 핵심차이
    // (기존) bytes[] bytes = file.getBytes(); file.wirte(..);
    // 1. 메모리 : getBytes()는 파일 '전체'를 byte[]로 힙 메모리에 올린다 -> 큰 파일 / 동시 업로드시 OOM 위험
    // 반면 transferTo는 통째로 올리지 않고 옮기며, 같은 디스크면 복사가 아니라 이동방식이라, 가볍고 빠르다

    public String storeFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {return null;}
        try {
            File dir = new File(uploadDir).getAbsoluteFile();
            if(!dir.exists()) { dir.mkdirs();}

            String storedFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            File dest = new File(dir, storedFileName);
            file.transferTo(dest);
            return storedFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
