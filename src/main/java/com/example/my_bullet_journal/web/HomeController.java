package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.services.QuoteService;
import com.example.my_bullet_journal.web.annotations.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final QuoteService quoteService;

    public HomeController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Home")
    public String showIndex(Model model){
        if(!model.containsAttribute("quoteViewModel")){
            model.addAttribute("quoteViewModel", this.quoteService.getRandomQuote());
        }

        return "index";

    }



    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public String showHomeAuth(Model model){
        if(!model.containsAttribute("quoteViewModel")){
            model.addAttribute("quoteViewModel", this.quoteService.getRandomQuote());
        }

        return "index";

    }
}
