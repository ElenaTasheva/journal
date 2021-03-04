package com.example.my_bullet_journal.models.entities;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="expenses")
public class Expense extends BudgetBaseEntity{

    private ExpenseEnum category;


    public Expense() {
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    public ExpenseEnum getCategory() {
        return category;
    }

    public Expense setCategory(ExpenseEnum category) {
        this.category = category;
        return this;
    }


}
