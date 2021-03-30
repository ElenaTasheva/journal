package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.entities.Role;
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



import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    User user;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;


    @BeforeEach
    public void setUp() {
        user = new User();
        roleService.seedRoles();
        user.setUsername("admin");
        user.setEmail("testing@abv.bg");
        user.setPassword("123");
        userService.seedAdmin();

    }

    @Test
    public void registerUser() throws Exception {
        this.mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterBindingModel", "userExistsError"));
        // register user only if it doesnt exist;
        try {
            userService.findByEmail("testing@abv.bg");
        } catch (UsernameNotFoundException ex) {
            userService.registerAndLogin(this.modelMapper.map(user, UserRegisterServiceModel.class));
            Assert.assertEquals(2, userRepository.count());
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void updateUserRole() throws Exception {
        this.mockMvc.perform(get("/users/roles/update/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model()
                        .attributeExists("topicBindingModel", "quoteBindingModel", "success"));

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
}
