package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.services.BudgetServiceModel;

import java.math.BigDecimal;
import java.util.HashMap;

public interface BudgetService {


    HashMap<String, BigDecimal> getExpensesList();
    HashMap<String, BigDecimal> getIncomeList();
    BigDecimal getIncomeSum();
    BigDecimal getExpensesSum();

    BigDecimal getBalance();
}
