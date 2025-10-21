package com.nabil.managers.controller;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nabil.managers.service.FileService;



@Controller
public class UserController {

    private final FileService fileService;

    public UserController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("Login page called");
        return "login";
    }
    

    @GetMapping("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<String> imageFileNames = fileService.getFilesName(username);

        model.addAttribute("imageFiles", imageFileNames);
        return "home";
    }
}
