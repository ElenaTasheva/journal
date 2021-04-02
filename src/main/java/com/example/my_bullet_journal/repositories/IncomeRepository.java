package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query(
           "SELECT i FROM Income i " +
                    "WHERE i.user.id = :id AND i.status = 'ACTIVE' " +
                    "ORDER BY i.category")
    List<Income> findAllAndOrderByCategory(Long id);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :id " +
            "AND i.status = 'ACTIVE'")
    BigDecimal getTotal(Long id);


    @Query("SELECT i.category, SUM(i.amount) " +
                    "FROM Income i WHERE i.user.id = :id AND i.status = 'ACTIVE' " +
                    "GROUP BY i.category")
    List<Object[]> sumIncomeByCategory(Long id);


    @Query(
            "SELECT i FROM Income i " +
                    "WHERE i.addedOn < :date AND i.status = 'ACTIVE'")
    List<Income> changeMonthlyStatus(LocalDate date);
}
