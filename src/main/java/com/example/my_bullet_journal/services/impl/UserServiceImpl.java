package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.models.view.UserViewModel;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.RoleService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel) {
        User user = modelMapper.map(userRegisterServiceModel, User.class);


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

    @Override
    public User findByEmail(String currentUserEmail) {
        return this.userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with this email does ot exist"));
    }

    @Override
    public List<UserViewModel> findAll() {
        return this.userRepository.findAll()
                .stream().map(u -> this.modelMapper.map(u, UserViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void makeAdmin(Long id) {

        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.getRoles().add(roleService.findByRow(RoleEnum.ADMIN).get());
        userRepository.save(user);

    }


}

