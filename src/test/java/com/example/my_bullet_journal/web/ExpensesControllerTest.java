package com.example.my_bullet_journal.web;


import com.example.my_bullet_journal.models.enums.ExpenseEnum;

import com.example.my_bullet_journal.repositories.ExpenseRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpensesControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN", "USER"})
    public void addExpenseAddsExpenseToTheRepoAndRedirects() throws Exception {
        long count = expenseRepository.count();
        this.mockMvc.perform(post("/expenses/add")
                .param("amount", String.valueOf(BigDecimal.valueOf(100)))
                .param("category", ExpenseEnum.HEALTH.name())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(count+1, expenseRepository.count());
        Assert.assertEquals(ExpenseEnum.HEALTH, expenseRepository.findById((long) 1).get().getCategory());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN", "USER"})
    public void showsAllExpenseAndTheTotal() throws Exception {
        this.mockMvc.perform(get("/expenses/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-expenses"))
                .andExpect(model()
                        .attributeExists("categories", "expenseBindingModel"));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN", "USER"})
    public void addExpenseDoesNOtAddExpenseToTheRepoWhenTheModelHasErrorsAndRedirects() throws Exception {
        long count = expenseRepository.count();
        this.mockMvc.perform(post("/expenses/add")
                .with(csrf()))
                .andExpect(flash().attributeExists("expenseBindingModel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add"));
        Assert.assertEquals(count, expenseRepository.count());
    }

}




