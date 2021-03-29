package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.LogExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogExceptionEntity,Long> {
}
