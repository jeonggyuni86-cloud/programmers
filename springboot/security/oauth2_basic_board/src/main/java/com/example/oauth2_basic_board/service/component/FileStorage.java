package com.example.oauth2_basic_board.service.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileStorage {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            File dir = new File(uploadDir).getAbsoluteFile();

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String storedFileName =
                    UUID.randomUUID() + "-" + file.getOriginalFilename();

            File dest = new File(dir, storedFileName);

            file.transferTo(dest);

            log.info(
                    "파일 저장 완료 original={}, stored={}",
                    file.getOriginalFilename(),
                    storedFileName
            );

            return storedFileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Resource download(String fileName) {
        try {
            File file =
                    new File(uploadDir, fileName);

            Resource resource =
                    new UrlResource(file.toURI());

            if (!resource.exists() || !resource.isReadable()) {
                throw new IOException("파일을 읽을 수 없습니다.");
            }

            return resource;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return;
        }

        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            return;
        }

        if (!file.delete()) {
            log.warn("파일 삭제 실패 file={}", fileName);
        }
    }
}
