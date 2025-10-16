package com.nabil.managers.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class UserController {

    @GetMapping("/login")
    public String login(){
        System.out.println("Login page called");
        return "login";
    }
    

    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }
}
