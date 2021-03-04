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
            value = "SELECT * FROM expenses ORDER BY category",
            nativeQuery = true)
    List<Expense> findAllAndOrderByCategory();

    @Query("SELECT SUM(e.amount) FROM Expense e")
    BigDecimal getTotal();

    @Query(
            value = "select category, SUM(amount) " +
                    "FROM expenses " +
                    "GROUP BY category",
            nativeQuery = true)
   List<Object[]> finExpensesByCategories();
}
