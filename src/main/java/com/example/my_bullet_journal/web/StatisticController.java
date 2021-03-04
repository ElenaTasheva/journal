package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.services.BudgetServiceModel;
import com.example.my_bullet_journal.services.BudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticController {

    private final BudgetService budgetService;

    public StatisticController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/statistic")
    public String showStatistic(Model model){
        model.addAttribute("budgetServiceModel", this.budgetService.getExpensesList());
        model.addAttribute("incomeCategories", this.budgetService.getIncomeList());
        model.addAttribute("income", this.budgetService.getIncomeSum());
        model.addAttribute("expense", this.budgetService.getExpensesSum());
        model.addAttribute("balance", this.budgetService.getBalance());


        return "statistic";
    }
}
