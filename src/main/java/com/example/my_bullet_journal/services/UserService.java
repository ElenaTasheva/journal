package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.User;

public interface UserService {

    User getUserByUsername(String name);
}
