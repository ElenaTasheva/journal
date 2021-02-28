package com.example.my_bullet_journal.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {


    @GetMapping("/login")
    public String showLogin(){

        return "login";

    }

    @GetMapping("/register")
    public String showRegister(){

        return "register";

    }
}
