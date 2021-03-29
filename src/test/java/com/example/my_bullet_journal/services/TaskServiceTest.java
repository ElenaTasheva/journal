package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.DailyTask;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.models.view.TaskViewModel;
import com.example.my_bullet_journal.repositories.TaskRepository;
import com.example.my_bullet_journal.services.impl.TaskServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TaskServiceTest {


    private TaskRepository mockedTaskRepository;
    private  ModelMapper modelMapper;
    private  UserService mockedUserService;

    @Before
    public void init(){
        this.mockedTaskRepository = Mockito.mock(TaskRepository.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.modelMapper = new ModelMapper();
    }





    @Test
    public void getAllTasksReturnsListOfTaskViewModel() {
        Mockito.when(mockedUserService.findByEmail("admin@abv.bg")).thenReturn((User) new User().setId((long) 1));
        Mockito.when(mockedTaskRepository.findAllByStatus(StatusEnum.INPROGRESS, (long) 1)).thenReturn(List.of(new DailyTask()));
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        List<TaskViewModel> tasks = taskService.getAllTasks("admin@abv.bg");
        Assert.assertEquals(1, tasks.size());
    }

    @Test
    public void changeStatusToCompletedWhenTaskIsPresent() {
        DailyTask task = new DailyTask();
        Mockito.when(mockedTaskRepository.findById((long) 1)).thenReturn(Optional.of(task));
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        taskService.changeStatusToCompleted((long)1);
        Assert.assertEquals(StatusEnum.COMPLETED, task.getStatus());
    }


    @Test(expected = IllegalArgumentException.class)
    public void changeStatusToCompletedThrowsExceptionWhenStatusDoesNotExistInDb() {
        Mockito.when(mockedTaskRepository.findById((long) 1)).thenReturn(Optional.empty());
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        taskService.changeStatusToCompleted((long)1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findByIdThrowsExceptionWhenStatusDoesNotExistInDb() {

        Mockito.when(mockedTaskRepository.findById((long) 1)).thenReturn(Optional.empty());
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        taskService.findById((long)1);
    }

    @Test
    public void findByIdReturnsTaskServiceModelWithId() {
        DailyTask task = new DailyTask();
        task.setId((long) 1);
        Mockito.when(mockedTaskRepository.findById((long) 1)).thenReturn(Optional.of(task));
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        TaskServiceModel taskServiceModel = taskService.findById((long)1);
        Assert.assertEquals(task.getId(), taskServiceModel.getId());

    }

    @Test
    public void updateTaskWithIdWhenExistInDb() {
        TaskServiceModel taskServiceModel = new TaskServiceModel();
        taskServiceModel.setName("testing");
        DailyTask task = new DailyTask();
        task.setName("none");
        Mockito.when(mockedTaskRepository.findById((long) 1)).thenReturn(Optional.of(task));
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        taskService.update((long) 1, taskServiceModel);
       // Assert.assertEquals("testing", task.getName());

        //todo test does not work correctly
    }

    @Test
    public void changeStatusOnPastDateTasksToExpired() {
        List<DailyTask> tasks = new ArrayList<>();
        DailyTask task = new DailyTask();
        tasks.add(task);
        task.setDueOn(LocalDate.of(2010, 12,12));
        task.setStatus(StatusEnum.INPROGRESS);
        Mockito.when(mockedTaskRepository.findAllTaskThatAreExpired(LocalDate.now()))
                .thenReturn(tasks);
        TaskService taskService = new TaskServiceImpl(mockedTaskRepository, modelMapper, mockedUserService);
        taskService.changeTaskStatusWhenStartingTheApp();
        Assert.assertEquals(StatusEnum.EXPIRED, task.getStatus());
    }



}
