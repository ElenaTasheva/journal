package com.example.my_bullet_journal.models.entities;

import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_tasks")
public class DailyTask extends BaseEntity{

    private String name;
    private LocalDate dueOn;
    private DailyCategoryEnum category;
    private String description;
    private StatusEnum status = StatusEnum.INPROGRESS;
    private User user;

    public DailyTask() {
    }


    @Column(unique = true)
    public String getName() {
        return name;
    }

    public DailyTask setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "due_on")
    public LocalDate getDueOn() {
        return dueOn;
    }

    public DailyTask setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public DailyCategoryEnum getCategory() {
        return category;
    }

    public DailyTask setCategory(DailyCategoryEnum category) {
        this.category = category;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public DailyTask setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public DailyTask setUser(User user) {
        this.user = user;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public DailyTask setDescription(String description) {
        this.description = description;
        return this;
    }
}
