package com.example.my_bullet_journal.models.bindings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class BaseBudgetBindingModel {

    private String description;
    private BigDecimal amount;


    public BaseBudgetBindingModel() {
    }

    public String getDescription() {
        return description;
    }

    public BaseBudgetBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }


    @Positive(message = "Amount must be a positive number")
    @NotNull(message = "Please enter an amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public BaseBudgetBindingModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
