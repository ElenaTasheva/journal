package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
