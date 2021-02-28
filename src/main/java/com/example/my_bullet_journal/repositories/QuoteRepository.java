package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findAll();
}
