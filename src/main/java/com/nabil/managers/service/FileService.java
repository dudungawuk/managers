package com.nabil.managers.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nabil.managers.entity.File;
import com.nabil.managers.entity.User;
import com.nabil.managers.repository.FileRepository;
import com.nabil.managers.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class FileService {
    
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository,UserRepository userRepository){
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Value("${file.upload.root-dir}")
    private String rootDir;

    public void storeFile(String username, MultipartFile file) throws IOException {
        
        Path userDirPath = Paths.get(rootDir, username);

        if (!Files.exists(userDirPath)) {
            Files.createDirectories(userDirPath);
        }
        
        String originalFilename = file.getOriginalFilename();
        Integer fileSize = (int) file.getSize();
        String fileType = file.getContentType();

        
        Path destinationPath = userDirPath.resolve(originalFilename);

        User user = userRepository.findByUsername(username);
        File userFile = new File(user,originalFilename,fileSize,fileType);
        fileRepository.save(userFile);


        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("File berhasil disimpan di: " + destinationPath.toAbsolutePath());
    }  

    public List<String> getFilesName(String username){
        User user = userRepository.findByUsername(username);
        String userId = user.getId();

        List<String> userFiles = fileRepository.getAllFileById(userId).stream()
                                    .map(file -> file.getName())
                                    .collect(Collectors.toList());

        return userFiles;
    }

} 