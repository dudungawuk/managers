package com.nabil.managers.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${file.upload.root-dir}")
    private String rootDir;

    public void storeFile(String username, MultipartFile file) throws IOException {
        
        Path userDirPath = Paths.get(rootDir, username);

        if (!Files.exists(userDirPath)) {
            Files.createDirectories(userDirPath);
        }
        
        String originalFilename = file.getOriginalFilename();
        
        Path destinationPath = userDirPath.resolve(originalFilename);

        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("File berhasil disimpan di: " + destinationPath.toAbsolutePath());
    }  
} 