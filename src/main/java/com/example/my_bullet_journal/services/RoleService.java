package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.enums.RoleEnum;

public interface RoleService {

    void seedRoles();
    Role findByRow(RoleEnum roleEnum);
}
