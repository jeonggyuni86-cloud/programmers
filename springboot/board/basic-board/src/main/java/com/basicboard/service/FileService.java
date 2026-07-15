package com.basicboard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.UUID;

@Slf4j
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

            log.info("파일 저장 : originalStoredFIleName = {}, storedFileName = {}", file.getOriginalFilename(), storedFileName);
            return storedFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource downloadFile(String fileName) {
        try {
            File baseUploadDir = new File(uploadDir).getAbsoluteFile();
            File file = new File(baseUploadDir, fileName);

            // 파일을 가리키는 Resource 생성 (실제로 읽어 들이는게 아니라 '위치만' 잡아 둔 상태)

            Resource resource = new UrlResource(file.toURI());
            if( !resource.exists() || !resource.isReadable() ) {
                throw new IOException("파일을 읽어오는데 실패 했습니다. : " + fileName);
            }
            return resource;

        } catch (MalformedInputException e) {
            throw new IllegalStateException("파일 경로가 잘못 되었습니다.");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filePath) {
        if(filePath == null || filePath.isBlank()) {
            return;
        }
        File file = new File(filePath);
        if(!file.exists()) { return; }
        boolean deleted = file.delete();

        if( !deleted ) {
            log.warn("첨부파일 삭제 실패(디스크에 남음) : filePath = {}", filePath);
        }
    }
}
