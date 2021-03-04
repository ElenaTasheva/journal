package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.view.IncomeViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query(
            value = "SELECT * FROM income ORDER BY category",
            nativeQuery = true)
    List<Income> findAllAndOrderByCategory();

    @Query("SELECT SUM(i.amount) FROM Income i")
    BigDecimal getTotal();


    @Query(
            value = "select category, SUM(amount) " +
                    "FROM income " +
                    "GROUP BY category",
            nativeQuery = true)
    List<Object[]> findIncomeByCategory();
}
