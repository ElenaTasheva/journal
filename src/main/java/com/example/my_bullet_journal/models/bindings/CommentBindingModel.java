package com.example.my_bullet_journal.models.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CommentBindingModel {
    private String text;

    public CommentBindingModel() {
    }

    @NotBlank(message = "Please add comment")
    public String getText() {
        return text;
    }

    public CommentBindingModel setText(String text) {
        this.text = text;
        return this;
    }
}
