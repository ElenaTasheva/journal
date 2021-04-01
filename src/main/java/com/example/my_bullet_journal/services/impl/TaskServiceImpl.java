package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.DailyTask;
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

import javax.annotation.PostConstruct;
import java.time.LocalDate;
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
    public void save(TaskServiceModel taskServiceModel, String email) {
        taskServiceModel.setUser(this.userService.findByEmail(email));
        DailyTask dailyTask = this.modelMapper.map(taskServiceModel, DailyTask.class);
        taskRepository.save(dailyTask);

    }

    @Override
    public List<TaskViewModel> getAllTasks(String email) {
        Long id = userService.findByEmail(email).getId();
        return this.taskRepository.findAllByStatus(StatusEnum.INPROGRESS, id)
                .stream().map(task -> {
                    return this.modelMapper.map(task, TaskViewModel.class);
                }).collect(Collectors.toList());
    }

    @Override
    public void changeStatusToCompleted(Long id) {
        Optional<DailyTask> task = taskRepository.findById(id);
        if(task.isPresent()){
            task.get().setStatus(StatusEnum.COMPLETED);
            this.taskRepository.save(task.get());
        }
        else throw new IllegalArgumentException(String.format("Task with id - %s does not exist",id));
    }

    @Override
    public TaskServiceModel findById(long taskId) {
        return this.modelMapper.map(this.taskRepository
                .findById(taskId).orElseThrow(IllegalArgumentException::new), TaskServiceModel.class);
    }

    @Override
    public void update(Long id, TaskServiceModel taskServiceModel) {
        DailyTask task = this.taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException((String.format("Task with id - %s does not exist",id))));
        taskServiceModel.setId(id);
        taskServiceModel.setUser(task.getUser());
        task = this.modelMapper.map(taskServiceModel, DailyTask.class);
        this.taskRepository.save(task);

    }

//checking if tasks in the past are not completed and mark them as expired
    @Scheduled(cron = "0 0 0 * * *")
    protected void changeTaskStatus() {
        this.taskRepository.findAllTaskThatAreExpired(LocalDate.now())
                .forEach(t -> {
                    t.setStatus(StatusEnum.EXPIRED);
                    taskRepository.save(t);
                });

    }

    // checking what has been expired when starting the app
    public void changeTaskStatusWhenStartingTheApp() {
        changeTaskStatus();

    }

    @Override
    public void seedTestTask() {
        if(taskRepository.count() == 0){
            DailyTask dailyTask = new DailyTask();
            dailyTask.setCategory(DailyCategoryEnum.WORK)
                    .setStatus(StatusEnum.INPROGRESS)
                    .setDueOn(LocalDate.of(2021, 04, 11))
                    .setName("Passing all tests...")
                    .setUser(userService.findById((long) 1));
            this.taskRepository.save(dailyTask);
        }
    }


}
