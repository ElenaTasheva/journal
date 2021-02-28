package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.enums.DailyCategoryEnum;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.models.view.TaskViewModel;

import java.util.List;

public interface TaskService {
    List<DailyCategoryEnum> getAllCategories();
    //TODO: change the return method
    void save(TaskServiceModel map);

    List<TaskViewModel> getAllTasks();

}