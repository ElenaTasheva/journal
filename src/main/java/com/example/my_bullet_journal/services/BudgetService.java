package com.example.my_bullet_journal.services;

import java.math.BigDecimal;
import java.util.HashMap;

public interface BudgetService {


    HashMap<String, BigDecimal> getExpensesList(String username);
    HashMap<String, BigDecimal> getIncomeList(String email);
    BigDecimal getIncomeSum();
    BigDecimal getExpensesSum();

    BigDecimal getBalance();
}
