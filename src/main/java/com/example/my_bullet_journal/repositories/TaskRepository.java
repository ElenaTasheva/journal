package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.DailyTask;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<DailyTask, Long> {

    Optional<DailyTask> findById(Long id);


    @Query("SELECT t FROM DailyTask t WHERE t.status = :status")
    List<DailyTask> findAllByStatus(StatusEnum status);

    List<DailyTask> findAll();

}
