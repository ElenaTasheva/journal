package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import java.math.BigDecimal;
import java.util.HashMap;

public class BudgetServiceModel {

    private HashMap<String, BigDecimal> expensesCategory;
    private HashMap<String, BigDecimal> incomeCategory;
    private BigDecimal sumExpenses;
    private BigDecimal sumIncome;

    public BudgetServiceModel() {
    }

    public HashMap<String, BigDecimal> getExpensesCategory() {
        return expensesCategory;
    }

    public BudgetServiceModel setExpensesCategory(HashMap<String, BigDecimal> expensesCategory) {
        this.expensesCategory = expensesCategory;
        return this;
    }

    public HashMap<String, BigDecimal> getIncomeCategory() {
        return incomeCategory;
    }

    public BudgetServiceModel setIncomeCategory(HashMap<String, BigDecimal> incomeCategory) {
        this.incomeCategory = incomeCategory;
        return this;
    }

    public BigDecimal getSumExpenses() {
        return sumExpenses;
    }

    public BudgetServiceModel setSumExpenses(BigDecimal sumExpenses) {
        this.sumExpenses = sumExpenses;
        return this;
    }

    public BigDecimal getSumIncome() {
        return sumIncome;
    }

    public BudgetServiceModel setSumIncome(BigDecimal sumIncome) {
        this.sumIncome = sumIncome;
        return this;
    }
}