package com.nabil.managers.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nabil.managers.service.FileService;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class FileManagementController {

    private final FileService fileService;

    public FileManagementController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(file.isEmpty()){
            return "redirect:/home?uploadError=File kosong.";
        }
        try {
            System.out.println("File '" + file.getOriginalFilename() + "' dari user " + username + " diterima.");
            fileService.storeFile(username,file);
        }catch (Exception e) {
            System.err.println("Gagal menyimpan file: " + e.getMessage());
            return "redirect:/home?uploadError=Gagal menyimpan file: " + e.getMessage();
        }
        return "redirect:/home?uploadSuccess=true";
    }
}
