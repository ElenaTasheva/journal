package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.bindings.IncomeBindingModel;
import com.example.my_bullet_journal.models.services.IncomeServiceModel;
import com.example.my_bullet_journal.services.IncomeService;
import com.example.my_bullet_journal.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final ModelMapper modelMapper;

    public IncomeController(IncomeService incomeService, ModelMapper modelMapper) {
        this.incomeService = incomeService;

        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Income")
    public String showIncome(Model model){
        if(!model.containsAttribute("incomeBindingModel")){
            model.addAttribute("incomeBindingModel", new IncomeBindingModel());
        }
        model.addAttribute("categories", this.incomeService.getCategories());
        return "add-income";
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(@Valid IncomeBindingModel incomeBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails user){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("incomeBindingModel", incomeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.incomeBindingModel", bindingResult);
            return "redirect:add";
        }

        incomeService.save(this.modelMapper.map(incomeBindingModel, IncomeServiceModel.class), user.getUsername());

        return "redirect:all";

    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("My Income")
    public String showAll(Model model, @AuthenticationPrincipal UserDetails user){
        model.addAttribute("income", this.incomeService.getAllIncomesOrderByCategory(user.getUsername()));
        model.addAttribute("total", this.incomeService.getTotalIncome(user.getUsername()));
        return "income";
    }
}
