package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.services.BudgetServiceModel;
import com.example.my_bullet_journal.services.BudgetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
public class StatisticController {

    private final BudgetService budgetService;

    public StatisticController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/statistic")
    @PreAuthorize("isAuthenticated()")
    public String showStatistic(Model model){
        model.addAttribute("budgetServiceModel", this.budgetService.getExpensesList());
        model.addAttribute("incomeCategories", this.budgetService.getIncomeList());
        model.addAttribute("income", this.budgetService.getIncomeSum());
        model.addAttribute("expense", this.budgetService.getExpensesSum());
        model.addAttribute("balance", this.budgetService.getBalance());
        model.addAttribute("emptyBalance", false);
        if(budgetService.getBalance().equals(BigDecimal.valueOf(0))){
            model.addAttribute("emptyBalance", true);
        }


        return "statistic";
    }
}
