package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.IncomeServiceModel;
import com.example.my_bullet_journal.repositories.IncomeRepository;
import com.example.my_bullet_journal.services.IncomeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IncomeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private IncomeRepository incomeRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void addIncomeAddsIncomeAndRedirects() throws Exception {
        this.mockMvc.perform(post("/income/add").with(csrf()))
                .andExpect(status().is3xxRedirection());
        if (incomeRepository.count() == 0) {
            incomeService.save((IncomeServiceModel) new IncomeServiceModel().setCategory(IncomeEnum.DEPOSIT)
                    .setAmount(BigDecimal.valueOf(200)), "admin@gmail.com");
        }
        Assert.assertEquals(1, incomeRepository.count());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void showsAllIncomeAndTheTotal() throws Exception {
        this.mockMvc.perform(get("/income/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-income"))
                .andExpect(model()
                        .attributeExists("categories", "incomeBindingModel"));
    }

    }



