package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;

public interface UserService {

    User getUserByUsername(String name);

    //todo return object
    void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel);

    void seedAdmin();

    User findByEmail(String currentUserEmail);
}
