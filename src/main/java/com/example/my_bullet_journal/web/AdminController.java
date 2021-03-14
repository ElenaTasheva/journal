package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.bindings.QuoteBingingModel;
import com.example.my_bullet_journal.models.bindings.TopicBindingModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String showAdmin(Model model){

        if(!model.containsAttribute("topicBindingModel")){
            model.addAttribute("topicBindingModel", new TopicBindingModel());
        }
        if(!model.containsAttribute("quoteBindingModel")){
            model.addAttribute("quoteBindingModel", new QuoteBingingModel());
            model.addAttribute("success", false);
        }

        return "admin";
    }
}
