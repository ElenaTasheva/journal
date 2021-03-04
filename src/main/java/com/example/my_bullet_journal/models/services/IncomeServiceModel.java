package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.enums.IncomeEnum;

public class IncomeServiceModel  extends BaseBudgetServiceModel{

    private IncomeEnum category;

    public IncomeEnum getCategory() {
        return category;
    }

    public IncomeServiceModel setCategory(IncomeEnum category) {
        this.category = category;
        return this;
    }
}
