package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseServiceModel extends BaseBudgetServiceModel {

    private ExpenseEnum category;


    public ExpenseServiceModel() {
    }

    public ExpenseEnum getCategory() {
        return category;
    }

    public ExpenseServiceModel setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }


}
