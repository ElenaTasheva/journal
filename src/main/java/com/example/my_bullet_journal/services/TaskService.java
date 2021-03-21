package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.bindings.TaskBindingModel;
import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.models.view.TaskViewModel;

import java.util.List;

public interface TaskService {
    List<DailyCategoryEnum> getAllCategories();

    void save(TaskServiceModel map, String email);

    List<TaskViewModel> getAllTasks(String email);

    void changeStatusToCompleted(Long id);

    TaskServiceModel findById(long taskId);

    void update(Long id, TaskServiceModel taskServiceModel);

    void changeTaskStatusWhenStartingTheApp();
}
