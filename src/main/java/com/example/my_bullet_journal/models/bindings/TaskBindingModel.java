package com.example.my_bullet_journal.models.bindings;

import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TaskBindingModel {

    private String name;
    private String description;
    private LocalDate dueOn;
    private DailyCategoryEnum category;

    public TaskBindingModel() {
    }

    @NotBlank(message = "Name can not be empty")
    public String getName() {
        return name;
    }

    public TaskBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @FutureOrPresent(message = "Date must be in the present or future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please select a date")
    public LocalDate getDueOn() {
        return dueOn;
    }

    public TaskBindingModel setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
        return this;
    }

    @NotNull(message = "Please select a category")
    public DailyCategoryEnum getCategory() {
        return category;
    }

    public TaskBindingModel setCategory(DailyCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
