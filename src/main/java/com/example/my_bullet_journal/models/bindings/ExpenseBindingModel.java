package com.example.my_bullet_journal.models.bindings;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import javax.validation.constraints.NotNull;


public class ExpenseBindingModel extends BaseBudgetBindingModel {

    private ExpenseEnum category;

    public ExpenseBindingModel() {
    }

    @NotNull(message = "Please choose category")
    public ExpenseEnum getCategory() {
        return category;
    }

    public ExpenseBindingModel setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }


}
