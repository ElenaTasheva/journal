package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.repositories.RoleRepository;
import com.example.my_bullet_journal.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String seedRoles() {
        if(this.roleRepository.count()==0){
            Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
                Role role = new Role(roleEnum);
                roleRepository.save(role);
            });
            return "Roles seeded successfully" ;

        }
        return "Roles has been seeded already";
    }

    @Override
    public Optional<Role> findByRow(RoleEnum roleEnum) {
        return this.roleRepository.findByRoleEnum(roleEnum);
    }
}
