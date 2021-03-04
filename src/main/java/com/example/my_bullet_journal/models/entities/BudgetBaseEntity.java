package com.example.my_bullet_journal.models.entities;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BudgetBaseEntity  extends BaseEntity{

    private String description;
    private LocalDate addedOn;
    private BigDecimal amount;
    private User user;

    public BudgetBaseEntity() {
    }



    @Column()
    public String getDescription() {
        return description;
    }

    public BudgetBaseEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column
    public LocalDate getAddedOn() {
        return addedOn;
    }

    public BudgetBaseEntity setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    @Column(name = "amount")
    @NotNull
    public BigDecimal getAmount() {
        return amount;
    }

    public BudgetBaseEntity setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public BudgetBaseEntity setUser(User user) {
        this.user = user;
        return this;
    }

    @PrePersist
    public void prePersist(){
        setAddedOn(LocalDate.now());
    }
}
