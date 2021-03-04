package com.example.my_bullet_journal.models.services;

public class TopicServiceModel {

    private Long id;
    private String title;

    public TopicServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public TopicServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TopicServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }
}
