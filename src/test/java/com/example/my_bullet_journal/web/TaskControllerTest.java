package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.TaskBindingModel;
import com.example.my_bullet_journal.models.entities.DailyTask;
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
import scala.collection.parallel.Task;

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

    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private TaskRepository taskRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void changeTaskStatusToCompletedById() throws Exception {
        if (taskRepository.count() == 0) {
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

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void editTaskActionDoesRedirectAndChangeTask() throws Exception {
        this.mockMvc.perform(post("/tasks/edit/{id}", 1).with(csrf()))
                .andExpect(status().is3xxRedirection());
        TaskBindingModel taskBindingModel
                = this.modelMapper.map(this.taskRepository.findById((long) 1).get(), TaskBindingModel.class);
        Assert.assertEquals(DailyCategoryEnum.CHORES, taskBindingModel.getCategory());
        taskBindingModel.setCategory(DailyCategoryEnum.FAMILY);
        taskService.update((long) 1, this.modelMapper.map(taskBindingModel, TaskServiceModel.class));
        Assert.assertEquals(DailyCategoryEnum.FAMILY, taskRepository.findById((long) 1).get().getCategory());
        // changing the status to the original so another test can be run;
        DailyTask task = taskRepository.findById((long) 1).get();
        task.setCategory(DailyCategoryEnum.CHORES);
        taskRepository.save(task);

    }
}
