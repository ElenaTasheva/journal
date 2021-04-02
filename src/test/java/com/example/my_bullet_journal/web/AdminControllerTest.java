package com.example.my_bullet_journal.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void showAdminForAdmin() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model()
                        .attributeExists("topicBindingModel", "quoteBindingModel", "success"));

    }

    @Test
    @WithMockUser(username = "admin")
    public void showAdminIsForbiddenForUserWithOnlyUSerRole() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());


    }
}
