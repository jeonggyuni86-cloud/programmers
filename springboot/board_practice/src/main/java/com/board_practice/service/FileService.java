package com.board_practice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

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
        file.delete();
    }
}
