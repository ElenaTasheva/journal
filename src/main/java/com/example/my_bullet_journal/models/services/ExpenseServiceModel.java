package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseServiceModel {

    private ExpenseEnum category;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public ExpenseServiceModel() {
    }

    public ExpenseEnum getCategory() {
        return category;
    }

    public ExpenseServiceModel setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ExpenseServiceModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public ExpenseServiceModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
