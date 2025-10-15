package com.nabil.managers.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("username", username);

        System.out.println("------------------HOME-------------------");

        System.out.println(authentication.getDetails().toString());
        System.out.println(authentication.getAuthorities().toString());
        System.out.println(authentication.getPrincipal().toString());

        return "home";
    }
}
