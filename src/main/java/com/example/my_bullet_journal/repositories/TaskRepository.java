package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.DailyTask;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<DailyTask, Long> {
}
