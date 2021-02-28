package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
