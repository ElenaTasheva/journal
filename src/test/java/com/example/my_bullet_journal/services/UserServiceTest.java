package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.impl.JournalDbUserService;
import com.example.my_bullet_journal.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

public class UserServiceTest {

    private  UserRepository mockedUserRepository;
    private  ModelMapper modelMapper;
    private  PasswordEncoder passwordEncoder;
    private  RoleService mockedRoleService;
    private  JournalDbUserService mockedJournalDbUserService;

    @Before
    public void init(){
        mockedUserRepository = Mockito.mock(UserRepository.class);
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        mockedRoleService = Mockito.mock(RoleService.class);
        mockedJournalDbUserService = Mockito.mock(JournalDbUserService.class);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void registerAndLoginThrowsExceptionWhenUserEmailIsAlreadyRegistered(){

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("This email is already registered");
        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
        userRegisterServiceModel.setPassword("123");
        Mockito.when(mockedUserRepository.findByEmail(userRegisterServiceModel.getEmail())).thenReturn(Optional.of(new User()));
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.registerAndLogin(userRegisterServiceModel);

    }


    @Test
    public void registerAndLoginThrowsExceptionWhenThereAreNoUserRoleToBeSetToTheUser(){

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("USER role not found. Please seed the roles.");
        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
        userRegisterServiceModel.setPassword("123");
        Mockito.when(mockedUserRepository.findByEmail(userRegisterServiceModel.getEmail())).thenReturn(Optional.empty());
        Mockito.when(mockedRoleService.findByRow(RoleEnum.USER)).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);

        userService.registerAndLogin(userRegisterServiceModel);

    }

    @Test
    public void seedAdminDoesNothingIfAdminIsAlreadyRegistered() {
        Mockito.when(mockedUserRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(new User()));
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.seedAdmin();

    }

    @Test(expected = IllegalArgumentException.class)
    public void seedAdminThrowsExceptionIfAdminRoleIsNotFound() {
        Mockito.when(mockedUserRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(mockedRoleService.findByRow(RoleEnum.ADMIN)).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.seedAdmin();

    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByEmailThrowsExceptionWhenUserWithThisEmailDoesNotExist() {
        Mockito.when(mockedUserRepository.findByEmail("test@mail.bg")).thenReturn(Optional.empty());
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.findByEmail("test@mail.bg");

    }

    @Test
    public void findByEmailWorksCorrectlyWhenUserExist() {
        Mockito.when(mockedUserRepository.findByEmail("test@mail.bg")).thenReturn(Optional.of(new User()));
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.findByEmail("test@mail.bg");


    }

    @Test
    public void makeAdminAddsAnAdminRoleToTheUser() {
        User user = new User();
        Role role = new Role(RoleEnum.USER);
        user.setRoles(new HashSet<>());
        user.getRoles().add(role);
        Assert.assertEquals(1, user.getRoles().size());
        Mockito.when(mockedUserRepository.findById((long) 1)).thenReturn(Optional.of(user));
        Mockito.when(mockedRoleService.findByRow(RoleEnum.ADMIN)).thenReturn(Optional.of(new Role(RoleEnum.ADMIN)));
        UserService userService = new UserServiceImpl(mockedUserRepository,modelMapper,passwordEncoder,mockedRoleService,mockedJournalDbUserService);
        userService.makeAdmin((long) 1);
        Assert.assertEquals(2, user.getRoles().size());

    }

}
