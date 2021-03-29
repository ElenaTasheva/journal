package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.BudgetServiceModel;
import com.example.my_bullet_journal.services.BudgetService;
import com.example.my_bullet_journal.services.ExpenseService;
import com.example.my_bullet_journal.services.IncomeService;
import com.example.my_bullet_journal.services.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class BudgedServiceImpl implements BudgetService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final BudgetServiceModel budgetServiceModel = new BudgetServiceModel();
    private final UserService userService;

    public BudgedServiceImpl(ExpenseService expenseService, IncomeService incomeService, UserService userService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.userService = userService;
    }

    @Override
    public HashMap<String, BigDecimal> getExpensesList(String email) {
        Long userId = this.userService.findByEmail(email).getId();
        budgetServiceModel.setExpensesCategory(this.expenseService.expenseSumByCategory(userId));
        addingNoDataParamIfTheSetIsEmpty(budgetServiceModel.getExpensesCategory());
        setAllCategories();
        setTotalSums(email);

        return budgetServiceModel.getExpensesCategory();
    }

    // if there is no data in income or expenses in order charts to be visible adding "No Data" parameter
    private void addingNoDataParamIfTheSetIsEmpty(HashMap<String, BigDecimal> map) {
        if(map.size() == 0){
            map.put("NO DATA", BigDecimal.valueOf(100));
        }
        else {
            map.put("NO DATA", BigDecimal.valueOf(0));
        }
    }


    @Override
    public HashMap<String, BigDecimal> getIncomeList(String email) {
        Long userId = this.userService.findByEmail(email).getId();
        budgetServiceModel.setIncomeCategory(this.incomeService.incomeSumByCategory(userId));
        addingNoDataParamIfTheSetIsEmpty(budgetServiceModel.getIncomeCategory());
        setAllCategoriesIncome(email);

        return budgetServiceModel.getIncomeCategory();
    }

    @Override
    public BigDecimal getIncomeSum() {
        return this.budgetServiceModel.getSumIncome() != null ? this.budgetServiceModel.getSumIncome() : BigDecimal.valueOf(0) ;
    }

    @Override
    public BigDecimal getExpensesSum() {
        return this.budgetServiceModel.getSumExpenses() != null ? this.budgetServiceModel.getSumExpenses() : BigDecimal.valueOf(0) ;
    }

    @Override
    public BigDecimal getBalance() {

            return getIncomeSum().subtract(getExpensesSum());

    }

    private void setAllCategoriesIncome(String email) {
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

    private void setTotalSums(String userEmail){
        this.budgetServiceModel.setSumExpenses(this.expenseService.getTotalAmountOfExpenses(userEmail));
        this.budgetServiceModel.setSumIncome(this.incomeService.getTotalIncome(userEmail));
    }

}
