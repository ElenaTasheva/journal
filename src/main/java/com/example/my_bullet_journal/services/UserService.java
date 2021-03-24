package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.models.view.UserViewModel;

import java.util.List;
import java.util.Optional;

public interface UserService {


    void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel);

    void seedAdmin();

    User findByEmail(String currentUserEmail);

    List<UserViewModel> findAll();

    User findById(Long id);

    void makeAdmin(Long id);
}
