package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.view.IncomeViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query(
            value = "SELECT * FROM income " +
                    "WHERE user_id = :id AND status = 'ACTIVE' " +
                    "ORDER BY category",
            nativeQuery = true)
    List<Income> findAllAndOrderByCategory(Long id);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :id " +
            "AND i.status = 'ACTIVE'")
    BigDecimal getTotal(Long id);


    @Query(
            value = "select category, SUM(amount) " +
                    "FROM income WHERE user_id = :id AND status = 'ACTIVE' " +
                    "GROUP BY category",
            nativeQuery = true)
    List<Object[]> findIncomeByCategory(Long id);


    @Query(value = "UPDATE Income i SET i.status = 'COMPLETED' " +
            "WHERE i.addedOn < :date AND i.status = 'ACTIVE'",
    nativeQuery = true)
    void changeMonthlyStatus(LocalDate date);
}
