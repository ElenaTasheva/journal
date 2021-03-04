package com.example.my_bullet_journal.models.view;

import com.example.my_bullet_journal.models.enums.IncomeEnum;

import java.math.BigDecimal;

public class IncomeViewModel {

    private long Id;
    private String description;
    private BigDecimal amount;
    private IncomeEnum category;

    public IncomeViewModel() {
    }

    public long getId() {
        return Id;
    }

    public IncomeViewModel setId(long id) {
        Id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IncomeViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public IncomeViewModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public IncomeEnum getCategory() {
        return category;
    }

    public IncomeViewModel setCategory(IncomeEnum category) {
        this.category = category;
        return this;
    }
}
