package com.example.my_bullet_journal.models.view;

import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;

import java.time.LocalDate;

public class TaskViewModel {


    private Long id;
    private String name;
    private LocalDate dueOn;
    private DailyCategoryEnum category;

    public TaskViewModel() {
    }


    public String getName() {
        return name;
    }

    public TaskViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public TaskViewModel setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
        return this;
    }

    public DailyCategoryEnum getCategory() {
        return category;
    }

    public TaskViewModel setCategory(DailyCategoryEnum category) {
        this.category = category;
        return this;
    }

    public Long getId() {
        return id;
    }

    public TaskViewModel setId(Long id) {
        this.id = id;
        return this;
    }
}
