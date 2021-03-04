package com.example.my_bullet_journal.models.view;

public class CommentViewModel {

    private Long id;
    private String text;
    private String username;


    public CommentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public CommentViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CommentViewModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
