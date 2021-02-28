package com.example.my_bullet_journal.models.bindings;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ExpenseBindingModel {

    private ExpenseEnum category;
    private String description;
    private BigDecimal amount;

    public ExpenseBindingModel() {
    }

  //  @NotNull(message = "Please choose category")
    public ExpenseEnum getCategory() {
        return category;
    }

    public ExpenseBindingModel setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Positive(message = "Amount must be a positive number")
    public BigDecimal getAmount() {
        return amount;
    }

    public ExpenseBindingModel setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
