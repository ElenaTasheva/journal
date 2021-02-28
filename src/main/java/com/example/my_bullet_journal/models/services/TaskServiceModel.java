package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;

import java.time.LocalDate;

public class TaskServiceModel {
    private String name;
    private LocalDate dueOn;
    private DailyCategoryEnum category;
    private String description;
    private StatusEnum status = StatusEnum.INPROGRESS;
    private User user;


    public TaskServiceModel() {
    }

    public String getName() {
        return name;
    }

    public TaskServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public TaskServiceModel setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
        return this;
    }

    public DailyCategoryEnum getCategory() {
        return category;
    }

    public TaskServiceModel setCategory(DailyCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public TaskServiceModel setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public User getUser() {
        return user;
    }

    public TaskServiceModel setUser(User user) {
        this.user = user;
        return this;
    }
}
