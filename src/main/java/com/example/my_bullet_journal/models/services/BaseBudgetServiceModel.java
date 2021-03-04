package com.example.my_bullet_journal.models.services;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class BaseBudgetServiceModel {

    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public BaseBudgetServiceModel() {
    }

    public String getDescription() {
        return description;
    }

    public BaseBudgetServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BaseBudgetServiceModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public BaseBudgetServiceModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
