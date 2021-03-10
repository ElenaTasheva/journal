package com.example.my_bullet_journal.models.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
public class Topic extends BaseEntity{

    private String title;
    private String imageUrl;
    private LocalDate addedOn;
    private List<Comment> comments;


    public Topic() {
    }

    public Topic(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    @Column(unique = true)
    @NotNull
    public String getTitle() {
        return title;
    }

    public Topic setTitle(String title) {
        this.title = title;
        return this;
    }
    @Column(name = "added_on")
    public LocalDate getAddedOn() {
        return addedOn;
    }

    public Topic setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    public List<Comment> getComments() {
        return comments;
    }

    public Topic setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Column
    public String getImageUrl() {
        return imageUrl;
    }

    public Topic setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @PrePersist
    public void prePersist(){
        setAddedOn(LocalDate.now());
    }
}
