package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.BudgetServiceModel;
import com.example.my_bullet_journal.services.BudgetService;
import com.example.my_bullet_journal.services.ExpenseService;
import com.example.my_bullet_journal.services.IncomeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class BudgedServiceImpl implements BudgetService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final BudgetServiceModel budgetServiceModel = new BudgetServiceModel();

    public BudgedServiceImpl(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @Override
    public HashMap<String, BigDecimal> getExpensesList() {
        budgetServiceModel.setExpensesCategory(this.expenseService.expensesByCategory());
        checkIfAllEmpty(budgetServiceModel.getExpensesCategory());
        setAllCategories();
        setTotalSums();

        return budgetServiceModel.getExpensesCategory();
    }

    private void checkIfAllEmpty(HashMap<String, BigDecimal> map) {
        if(map.size() == 0){
            map.put("NO DATA", BigDecimal.valueOf(100));
        }
        else {
            map.put("NO DATA", BigDecimal.valueOf(0));
        }
    }


    @Override
    public HashMap<String, BigDecimal> getIncomeList() {
        budgetServiceModel.setIncomeCategory(this.incomeService.incomeByCategory());
        checkIfAllEmpty(budgetServiceModel.getIncomeCategory());
        setAllCategoriesIncome();

        return budgetServiceModel.getIncomeCategory();
    }

    @Override
    public BigDecimal getIncomeSum() {
        return this.budgetServiceModel.getSumIncome();
    }

    @Override
    public BigDecimal getExpensesSum() {
        return this.budgetServiceModel.getSumExpenses();
    }

    @Override
    public BigDecimal getBalance() {
        return this.budgetServiceModel.getSumIncome().subtract(this.budgetServiceModel.getSumExpenses());
    }

    private void setAllCategoriesIncome() {
        Arrays.stream(IncomeEnum.values())
                .forEach(value -> {
                    budgetServiceModel.getIncomeCategory()
                            .computeIfAbsent(String.valueOf(value), k -> BigDecimal.valueOf(0.0));
                });
    }


    private void setAllCategories() {
        Arrays.stream(ExpenseEnum.values())
                .forEach(value -> {
                    budgetServiceModel.getExpensesCategory()
                            .computeIfAbsent(String.valueOf(value), k -> BigDecimal.valueOf(0.0));
                });

    }

    private void setTotalSums(){
        this.budgetServiceModel.setSumExpenses(this.expenseService.getTotalAmountOfExpenses());
        this.budgetServiceModel.setSumIncome(this.incomeService.getTotalIncome());
    }

}
