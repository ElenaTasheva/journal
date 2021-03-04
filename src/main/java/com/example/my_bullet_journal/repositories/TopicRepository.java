package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t.title FROM Topic t WHERE t.id = :id")
    String findTopicNameById(Long id);

    Topic findByTitle(String title);
}
