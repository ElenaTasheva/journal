package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.enums.RoleEnum;

import java.util.Optional;

public interface RoleService {

    void seedRoles();
    Optional<Role> findByRow(RoleEnum roleEnum);
}
