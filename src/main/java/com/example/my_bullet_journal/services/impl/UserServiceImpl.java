package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.RoleService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void register(UserRegisterServiceModel userRegisterServiceModel) {
        Role userRole = this.roleService.findByRow(userRegisterServiceModel.getRoles().stream()
                .findFirst().get());

        User user = this.modelMapper.map(userRegisterServiceModel, User.class);
        String password = passwordEncoder.encode(userRegisterServiceModel.getPassword());
        user.setRoles(Set.of(userRole));
        user.setPassword(password);
        userRepository.save(user);

        //todo fix the role setting
    }
}
