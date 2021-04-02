package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.repositories.TaskRepository;
import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.TaskService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

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




    @BeforeEach
    public void init() {
        if (taskRepository.count() == 0) {
            taskService.save(new TaskServiceModel()
                    .setStatus(StatusEnum.INPROGRESS)
                    .setCategory(DailyCategoryEnum.CHORES).setDueOn(LocalDate.now()), "admin@gmail.com");
        }

    }


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"USER", "ADMIN"})
    public void changeTaskStatusToCompletedById() throws Exception {
        this.mockMvc.perform(post("/tasks/mark_done/{taskId}", 1).with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(StatusEnum.COMPLETED, taskService.findById(1).getStatus());
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"USER", "ADMIN"})
    public void addTaskShowsAForm() throws Exception {
        this.mockMvc.perform(get("/tasks/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"))
                .andExpect(model().attributeExists("taskBindingModel", "categories"));
    }


    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"USER", "ADMIN"})
    public void addTaskRedirectAndAddTasksIfTaskBindingDontHaveErrors() throws Exception {
        long count = taskRepository.count();
        this.mockMvc.perform(post("/tasks/add")
                .param("name", "new Task")
                .param("dueOn", String.valueOf(LocalDate.now()))
                .param("category",DailyCategoryEnum.FAMILY.name())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        Assert.assertEquals(count + 1, taskRepository.count());

    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"USER", "ADMIN"})
    public void editTaskActionDoesRedirectAndChangeTask() throws Exception {
        Assert.assertEquals(DailyCategoryEnum.WORK, taskService.findById(1).getCategory());
        this.mockMvc.perform(post("/tasks/edit/{id}", 1)
                .param("name", "changed")
                .param("dueOn", String.valueOf(LocalDate.now()))
                .param("category",DailyCategoryEnum.FAMILY.name())
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        Assert.assertEquals(DailyCategoryEnum.FAMILY, taskService.findById(1).getCategory());

    }
}
