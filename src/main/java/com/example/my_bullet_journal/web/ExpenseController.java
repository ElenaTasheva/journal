package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.ExpenseBindingModel;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.services.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("expenseBindingModel", expenseBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.expenseBindingModel", bindingResult);
            return "redirect:add";
            }

        expenseService.save(this.modelMapper.map(expenseBindingModel, ExpenseServiceModel.class));

        return "redirect:all";

    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public String showAll(Model model){
        model.addAttribute("expenses", this.expenseService.getAllExpenses());
        model.addAttribute("total", this.expenseService.getTotalAmountOfExpenses());
        return "expenses";
    }


}
