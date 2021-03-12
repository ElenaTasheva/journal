package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.bindings.TaskBindingModel;
import com.example.my_bullet_journal.models.entities.DailyTask;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.enums.StatusEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.models.view.TaskViewModel;
import com.example.my_bullet_journal.repositories.TaskRepository;
import com.example.my_bullet_journal.services.TaskService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, UserService userService) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<DailyCategoryEnum> getAllCategories() {
        return Arrays.stream(DailyCategoryEnum.values()).collect(Collectors.toList());
    }

    @Override
    public void save(TaskServiceModel taskServiceModel) {
        DailyTask dailyTask = this.modelMapper.map(taskServiceModel, DailyTask.class);
        //todo fix the user
        dailyTask.setUser(this.userService.getUserByUsername("pesho123"));
        taskRepository.save(dailyTask);

    }

    @Override
    public List<TaskViewModel> getAllTasks() {
        //todo show allwith the userid = current user
        return this.taskRepository.findAllByStatus(StatusEnum.INPROGRESS)
                .stream().map(task -> {
                 TaskViewModel view =  this.modelMapper.map(task, TaskViewModel.class);
                 return view;
                }).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        //todo don`t remove it but change status to completed
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
        DailyTask task = this.taskRepository.findById(id).orElse(null);
        taskServiceModel.setId(id);
        task = this.modelMapper.map(taskServiceModel, DailyTask.class);
        //todo fix the user
        task.setUser(this.userService.getUserByUsername("pesho123"));
        this.taskRepository.save(task);

    }
}
