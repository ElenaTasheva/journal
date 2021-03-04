package com.example.my_bullet_journal.models.bindings;

public class CommentBindingModel {
    private String text;

    public CommentBindingModel() {
    }

    public String getText() {
        return text;
    }

    public CommentBindingModel setText(String text) {
        this.text = text;
        return this;
    }
}
