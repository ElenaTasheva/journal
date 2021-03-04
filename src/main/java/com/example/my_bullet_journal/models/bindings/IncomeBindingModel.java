package com.example.my_bullet_journal.models.bindings;

import com.example.my_bullet_journal.models.enums.IncomeEnum;

import javax.validation.constraints.NotNull;

public class IncomeBindingModel extends BaseBudgetBindingModel {

    private IncomeEnum category;

    public IncomeBindingModel() {
    }

    @NotNull(message = "Please select category")
    public IncomeEnum getCategory() {
        return category;
    }

    public IncomeBindingModel setCategory(IncomeEnum category) {
        this.category = category;
        return this;
    }
}
