package com.example.my_bullet_journal.models.bindings;

import javax.validation.constraints.NotEmpty;

public class QuoteBingingModel {
    private String text;
    private String author;


    public QuoteBingingModel() {
    }

    @NotEmpty(message = "Please enter quote")
    public String getText() {
        return text;
    }

    public QuoteBingingModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public QuoteBingingModel setAuthor(String author) {
        this.author = author;
        return this;
    }
}
