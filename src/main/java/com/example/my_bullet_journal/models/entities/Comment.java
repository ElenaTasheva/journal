package com.example.my_bullet_journal.models.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    private User user;
    private Topic topic;
    private String text;
    private LocalDate addedOn;


    public Comment() {
    }

    @ManyToOne
    @NotNull
    public User getUser() {
        return user;
    }

    public Comment setUser(User user) {
        this.user = user;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    public Topic getTopic() {
        return topic;
    }

    public Comment setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    @Column(columnDefinition = "text")
    @NotNull
    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    @Column
    public LocalDate getAddedOn() {
        return addedOn;
    }

    public Comment setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
        return this;
    }

    @PrePersist
    public void prePersist(){
        setAddedOn(LocalDate.now());
    }
}
