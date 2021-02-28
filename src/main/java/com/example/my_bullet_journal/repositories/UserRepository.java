package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
