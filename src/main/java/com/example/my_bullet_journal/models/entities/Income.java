package com.example.my_bullet_journal.models.entities;

import com.example.my_bullet_journal.models.enums.IncomeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "income")
public class Income extends BudgetBaseEntity {


    private IncomeEnum category;

    public Income() {
    }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please choose category")
    public IncomeEnum getCategory() {
        return category;
    }

    public Income setCategory(IncomeEnum category) {
        this.category = category;
        return this;
    }
}
