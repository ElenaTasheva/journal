package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("SELECT c FROM Comment c WHERE Topic.id = 1")
//    List<Comment> findAllByTopicId(@Param("topicId") Long topicId);

    List<Comment> findAllById(Long id);

    List<Comment> findAllByTopic_Id(Long id);

    @Query("SELECT c.id from Comment c WHERE c.topic.title = :name")
    Long getIdByName(String name);
}
