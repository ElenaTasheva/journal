package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.repositories.RoleRepository;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.RoleService;
import com.example.my_bullet_journal.services.UserService;
import org.junit.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleService roleService;


    @BeforeEach
    public void init() {
        roleService.seedRoles();
        userService.seedAdmin();
    }

    @Test
    public void registerUserWorksCorrectly() throws Exception {
        this.mockMvc.perform(post("/users/register")
                .param("username", "123")
                .param("email", "workkkkkkk@123.com")
                .param("password", String.valueOf(123))
                .param("confirmedPassword", String.valueOf(123))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

           Assert.assertEquals("workkkkkkk@123.com", userRepository.findByEmail("workkkkkkk@123.com").get().getEmail());

    }


    @Test
    public void registerDoesNotRegisterUserWhenUserExists() throws Exception {
        long count = userRepository.count();
        this.mockMvc.perform(post("/users/register")
                .param("username", "123")
                .param("email", "admin@gmail.com")
                .param("password", String.valueOf(123))
                .param("confirmedPassword", String.valueOf(123))
                .with(csrf()))
                .andExpect(flash().attribute("userExistsError", true))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(count, userRepository.count());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void updateUserRole() throws Exception {
        registerNormalUser();
        long id = userRepository.findByEmail("test@").get().getId();
        this.mockMvc.perform(get("/users/roles/update/{1}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model()
                        .attributeExists("topicBindingModel", "quoteBindingModel", "success"));
        Assert.assertEquals(2, userRepository.findByEmail("test@").get().getRoles().size());

    }


    @Test
    public void loginError() throws Exception {
        this.mockMvc.perform(post("/users/login-error").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("bad_credentials"));

    }

    private void registerNormalUser() {
        User user = new User();
        user.setEmail("test@")
                .setUsername("test")
                .setRoles(Set.of(roleService.findByRow(RoleEnum.USER).get()));
        this.userRepository.save(user);
    }
}
