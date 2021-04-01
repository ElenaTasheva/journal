package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    @Query("SELECT u from User u WHERE u.roles.size = 1")
    Collection<User> findAllWithOnlyOneRole();
}
