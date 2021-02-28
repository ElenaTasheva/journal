package com.example.my_bullet_journal.models.services;

import com.example.my_bullet_journal.models.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;

public class UserRegisterServiceModel {

    private String username;
    private String email;
    private String password;
    private Set<RoleEnum> roles = new HashSet<>();

    public UserRegisterServiceModel() {
        roles.add(RoleEnum.USER);
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public UserRegisterServiceModel setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
        return this;
    }
}
