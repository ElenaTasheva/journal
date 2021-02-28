package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.TaskBindingModel;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public String showTasks(Model model){
        model.addAttribute("tasks", taskService.getAllTasks());
        return "all-tasks";

    }

    @GetMapping("/add")
    public String showAddTask(Model model){
        if(!model.containsAttribute("taskBindingModel")){
            model.addAttribute("taskBindingModel", new TaskBindingModel());
        }
        model.addAttribute("categories", taskService.getAllCategories());
        return "add-task";

    }

    @PostMapping("/add")
    public String addTask(@Valid TaskBindingModel taskBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){

        }
        this.taskService.save(this.modelMapper.map(taskBindingModel, TaskServiceModel.class));

        return "redirect:add";

    }
}
