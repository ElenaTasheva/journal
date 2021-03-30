package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.entities.Role;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.repositories.RoleRepository;
import com.example.my_bullet_journal.repositories.TaskRepository;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.TaskService;
import com.example.my_bullet_journal.services.TopicService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private TaskService taskService;




    @Autowired
    private TaskRepository taskRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void changeTaskStatusToCompletedById() throws Exception {
        if(taskRepository.count() == 0) {
            taskService.save(new TaskServiceModel().setCategory(DailyCategoryEnum.CHORES).setDueOn(LocalDate.now()), "admin@gmail.com");
        }
        taskService.findById(1).setStatus(StatusEnum.INPROGRESS);
        this.mockMvc.perform(delete("/tasks/delete/{taskId}", 1).with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(StatusEnum.COMPLETED, taskService.findById(1).getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void addStatusShowsAForm() throws Exception {
        this.mockMvc.perform(get("/tasks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"))
                .andExpect(model().attributeExists("taskBindingModel", "categories"));
    }



}
