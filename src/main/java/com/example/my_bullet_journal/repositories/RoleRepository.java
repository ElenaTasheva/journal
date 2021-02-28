package com.example.my_bullet_journal.repositories;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleEnum(RoleEnum roleEnum);
}
