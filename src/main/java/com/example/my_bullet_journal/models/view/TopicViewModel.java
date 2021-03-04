package com.example.my_bullet_journal.models.view;

import com.example.my_bullet_journal.models.entities.Comment;

import java.time.LocalDate;
import java.util.List;

public class TopicViewModel {

    private Long id;
    private String title;
    private List<Comment> comments;
    private String imageUrl;

    public TopicViewModel() {
    }

    public Long getId() {
        return id;
    }

    public TopicViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TopicViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public TopicViewModel setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public TopicViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
