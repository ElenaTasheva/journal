package com.example.my_bullet_journal.models.services;

public class UserLoginServiceModel {

    private String email;
    private String password;

    public UserLoginServiceModel() {
    }

    public String getEmail() {
        return email;
    }

    public UserLoginServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
