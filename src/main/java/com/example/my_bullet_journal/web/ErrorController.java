package com.example.my_bullet_journal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/show/error")
    public String showerrror(){
        return "error-404";    }
}
