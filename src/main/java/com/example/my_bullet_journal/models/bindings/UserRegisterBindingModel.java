package com.example.my_bullet_journal.models.bindings;

import com.example.my_bullet_journal.models.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@FieldMatch(
        first = "password",
        second = "confirmedPassword"
)
public class UserRegisterBindingModel {

    private String username;
    private String email;
    private String password;
    private String confirmedPassword;

    public UserRegisterBindingModel() {
    }

    @Size(min = 3, max = 20 , message = "Username must be between 3 and 10 characters")
    @NotBlank(message = "Username can not be empty")
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email
    @NotBlank(message = "Email can not be empty")
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 3, max = 10 , message = "Password must be between 3 and 10 characters")
    @NotBlank(message = "Password can not be empty")
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
    @Size(min = 3, max = 10 , message = "Password must be between 3 and 10 characters")
    @NotBlank(message = "Password can not be empty")
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public UserRegisterBindingModel setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
        return this;
    }


}
