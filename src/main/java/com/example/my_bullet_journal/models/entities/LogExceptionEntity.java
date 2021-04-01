package com.example.my_bullet_journal.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "exceptions")
public class LogExceptionEntity extends BaseEntity{

    private LocalDateTime localDateTime;
    private String message;

    public LogExceptionEntity() {
    }

    @Column(name = "date_time")
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LogExceptionEntity setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    @Column(name = "error_message", columnDefinition = "TEXT")
    public String getMessage() {
        return message;
    }

    public LogExceptionEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    @PrePersist
    private void setLocaleDateTime(){
        setLocalDateTime(LocalDateTime.now());
    }
}
