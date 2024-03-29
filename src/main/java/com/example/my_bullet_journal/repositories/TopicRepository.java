package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {




    Optional<Topic> findByTitle(String title);
}
