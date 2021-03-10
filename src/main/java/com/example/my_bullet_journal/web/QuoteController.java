package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.QuoteBingingModel;
import com.example.my_bullet_journal.services.QuoteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }


    @PostMapping("/quotes/add")
    public String addQuote(@Valid QuoteBingingModel quoteBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("quoteBindingModel", quoteBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.quoteBindingModel", bindingResult);
            return "redirect:/admin";
        }
        quoteService.add(quoteBindingModel);
        redirectAttributes.addAttribute("success", true);
        return "redirect:/admin";
        }
    }

