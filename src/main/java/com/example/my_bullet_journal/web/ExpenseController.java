package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.ExpenseBindingModel;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.services.ExpenseService;
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
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Expenses - add")


    public String showAdd(Model model){
        if(!model.containsAttribute("expenseBindingModel")){
            model.addAttribute("expenseBindingModel", new ExpenseBindingModel());
        }
        model.addAttribute("categories",this.expenseService.getCategories());

        return "add-expenses";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(@Valid ExpenseBindingModel expenseBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails user){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("expenseBindingModel", expenseBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.expenseBindingModel", bindingResult);
            return "redirect:add";
            }

        expenseService.save(this.modelMapper.map(expenseBindingModel, ExpenseServiceModel.class), user.getUsername());

        return "redirect:all";

    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Expenses")
    public String showAll(Model model, @AuthenticationPrincipal UserDetails user){
        model.addAttribute("expenses", this.expenseService.getAllExpenses(user.getUsername()));
        model.addAttribute("total", this.expenseService.getTotalAmountOfExpenses(user.getUsername()));
        return "expenses";
    }


}
