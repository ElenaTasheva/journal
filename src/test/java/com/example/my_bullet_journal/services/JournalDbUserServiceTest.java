package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.impl.JournalDbUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

public class JournalDbUserServiceTest {


    UserRepository mockedUserRepository;

    @Before
    public void init() {
        mockedUserRepository = Mockito.mock(UserRepository.class);
    }

    @Test(expected =  UsernameNotFoundException.class)
    public void loadUserByUserNameThrowsExceptionIfUserDoesNotExist(){
        Mockito.when(mockedUserRepository.findByEmail("test@mail.bg"))
                .thenReturn(Optional.empty());
        JournalDbUserService journalDbUserService = new JournalDbUserService(mockedUserRepository);
        journalDbUserService.loadUserByUsername("test@mail.bg");
    }

    @Test
    public void loadUserByUserNameReturnsUserDetailsWhenUserExist(){
        User user = new User();
        user.setRoles(new HashSet<>());
        user.setEmail("test");
        user.setPassword("test");
        Mockito.when(mockedUserRepository.findByEmail("test@mail.bg"))
                .thenReturn(Optional.of(user));
        JournalDbUserService journalDbUserService = new JournalDbUserService(mockedUserRepository);
        UserDetails userDetails = journalDbUserService.loadUserByUsername("test@mail.bg");
    }
}
