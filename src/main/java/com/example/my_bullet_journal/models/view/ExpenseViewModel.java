package com.example.my_bullet_journal.models.view;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import java.math.BigDecimal;

public class ExpenseViewModel {
    
    private long Id; 
    private String description;
    private BigDecimal amount;
    private ExpenseEnum category;


    public ExpenseViewModel() {
    }

    public long getId() {
        return Id;
    }

    public ExpenseViewModel setId(long id) {
        Id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ExpenseViewModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ExpenseEnum getCategory() {
        return category;
    }

    public ExpenseViewModel setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }
}
