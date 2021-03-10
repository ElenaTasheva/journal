package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.RoleService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private  final JournalDbUserService journalDbUserService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService, JournalDbUserService journalDbUserService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.journalDbUserService = journalDbUserService;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel) {
        User user = modelMapper.map(userRegisterServiceModel, User.class);



        // throw an error if email is already registered
        if(userRepository.findByEmail(userRegisterServiceModel.getEmail()).isPresent()){
            throw  new IllegalArgumentException("This email is already registered");
        }

            user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));


        Role userRole =  roleService.findByRow(RoleEnum.USER)
                .orElseThrow(
                        () -> new IllegalStateException("USER role not found. Please seed the roles."));

        user.setRoles(Set.of(userRole));
        user = userRepository.save(user);

        UserDetails principal = journalDbUserService.loadUserByUsername(user.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void seedAdmin() {
        //todo change the name to admin
        if(userRepository.findByEmail("admin@gmail.com").isEmpty()){
        User user = new User();
        user.setEmail("admin@gmail.com")
                .setUsername("admin")
                .setPassword(this.passwordEncoder.encode("admin"));
        Role userRole = roleService.findByRow(RoleEnum.USER).get();
        Role adminRole = roleService.findByRow(RoleEnum.ADMIN).get();
        user.setRoles(Set.of(userRole, adminRole));
        userRepository.save(user);
    }
    }


}

