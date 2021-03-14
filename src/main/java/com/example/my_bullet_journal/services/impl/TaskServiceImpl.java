package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.DailyTask;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.models.view.TaskViewModel;
import com.example.my_bullet_journal.repositories.TaskRepository;
import com.example.my_bullet_journal.services.TaskService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final  JournalDbUserService journalDbUserService;
    private User user;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, UserService userService, JournalDbUserService journalDbUserService) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.journalDbUserService = journalDbUserService;
    }

    @Override
    public List<DailyCategoryEnum> getAllCategories() {
        return Arrays.stream(DailyCategoryEnum.values()).collect(Collectors.toList());
    }

    @Override
    public void save(TaskServiceModel taskServiceModel) {
        taskServiceModel.setUser(user);
        DailyTask dailyTask = this.modelMapper.map(taskServiceModel, DailyTask.class);
        taskRepository.save(dailyTask);

    }

    @Override
    public List<TaskViewModel> getAllTasks() {
        user = getCurrentUser();
        Long id = user.getId();
        return this.taskRepository.findAllByStatus(StatusEnum.INPROGRESS, id)
                .stream().map(task -> {
                 TaskViewModel view =  this.modelMapper.map(task, TaskViewModel.class);
                 return view;
                }).collect(Collectors.toList());
    }

    @Override
    public void changeStatusToCompleted(Long id) {
        Optional<DailyTask> task = taskRepository.findById(id);
        if(task.isPresent()){
            task.get().setStatus(StatusEnum.COMPLETED);
            this.taskRepository.save(task.get());
        }
    }

    @Override
    public TaskServiceModel findById(long taskId) {
        //todo deal with the optional
        return this.modelMapper.map(this.taskRepository.findById(taskId).get(), TaskServiceModel.class);
    }

    @Override
    public void update(Long id, TaskServiceModel taskServiceModel) {
        DailyTask task = this.taskRepository.findById(id).orElseThrow(NullPointerException::new);
        taskServiceModel.setId(id);
        taskServiceModel.setUser(user);
        task = this.modelMapper.map(taskServiceModel, DailyTask.class);
        this.taskRepository.save(task);

    }

    @Override
    @Scheduled(cron = "0 59 23 * * *")
    public void changeTaskStatus() {
        this.taskRepository.findAllTaskThatAreGoingToExpired(Instant.now())
                .stream()
                .peek(t -> {
                    t.setStatus(StatusEnum.EXPIRED);
                    taskRepository.save(t);
                });

    }


    private User  getCurrentUser(){
        return userService.findByEmail(this.journalDbUserService.getCurrentUserEmail());

    }


}
