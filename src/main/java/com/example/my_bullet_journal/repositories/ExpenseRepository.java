package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Expense;
import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(
            value = "SELECT * FROM expenses " +
                    "WHERE user_id = :userId AND status = 'ACTIVE' " +
                    "ORDER BY category",
            nativeQuery = true)
    List<Expense> findAllAndOrderByCategory(Long userId);

    @Query("SELECT SUM(e.amount) FROM Expense e " +
            "WHERE e.user.id = :userId AND e.status = 'ACTIVE' ")
    BigDecimal getTotal(Long userId);

    @Query(
            value = "select category, SUM(amount) " +
                    "FROM expenses WHERE user_id = :userId AND status = 'ACTIVE' " +
                    "GROUP BY category",
            nativeQuery = true)
   List<Object[]> finExpensesByCategories(Long userId);
}
