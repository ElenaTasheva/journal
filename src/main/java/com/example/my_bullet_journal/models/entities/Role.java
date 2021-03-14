package com.example.my_bullet_journal.models.entities;


import com.example.my_bullet_journal.models.enums.RoleEnum;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role extends BaseEntity {

    private RoleEnum roleEnum;

    public Role() {
    }

    public Role(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    @Enumerated(EnumType.STRING)
    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public Role setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
        return this;
    }


}
