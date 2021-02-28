package com.example.my_bullet_journal.models.entities;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="expenses")
public class Expense extends BaseEntity{

    private ExpenseEnum category;
    private String description;
    private LocalDate addedOn;
    private BigDecimal amount;
    private User user;

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

    @Column()
    public String getDescription() {
        return description;
    }

    public Expense setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column
    public LocalDate getAddedOn() {
        return addedOn;
    }

    public Expense setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    @Column(name = "amount")
    @NotNull
    public BigDecimal getAmount() {
        return amount;
    }

    public Expense setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public Expense setUser(User user) {
        this.user = user;
        return this;
    }

    @PrePersist
    public void prePersist(){
        setAddedOn(LocalDate.now());
    }
}
