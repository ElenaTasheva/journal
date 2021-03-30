package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.TaskBindingModel;
import com.example.my_bullet_journal.models.services.TaskServiceModel;
import com.example.my_bullet_journal.services.TaskService;
import jdk.jshell.execution.Util;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public String showTasks(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("tasks", taskService.getAllTasks(user.getUsername()));
        model.addAttribute("noTaskFound", false);
        if(taskService.getAllTasks(user.getUsername()).size()==0){
            model.addAttribute("noTaskFound", true);
        }
        return "all-tasks";

    }

    @GetMapping("/add")
    public String showAddTask(Model model) {
        if (!model.containsAttribute("taskBindingModel")) {
            model.addAttribute("taskBindingModel", new TaskBindingModel());
        }
        model.addAttribute("categories", taskService.getAllCategories());
        return "add-task";

    }

    @PostMapping("/add")
    public String addTask(@Valid TaskBindingModel taskBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails user) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskBindingModel", taskBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskBindingModel", bindingResult);
            return "redirect:add";
        }

        this.taskService.save(this.modelMapper.map(taskBindingModel, TaskServiceModel.class), user.getUsername());

        return "redirect:all";

    }


    @DeleteMapping("/delete/{taskId}")
    public String changeTaskStatusToCompleted(@PathVariable long taskId) {
        taskService.changeStatusToCompleted(taskId);
        return "redirect:/tasks/all";
    }




    @PostMapping("/edit/{taskId}")
    public String editAction(@PathVariable long taskId,
                                   @Valid TaskBindingModel taskBindingModel,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskBindingModel", taskBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskBindingModel", bindingResult);
            return "redirect:" + taskBindingModel.getId();
        }

        this.taskService.update(taskId, this.modelMapper.map(taskBindingModel, TaskServiceModel.class));
        return "redirect:/tasks/all";
    }


    @GetMapping("/edit/{taskId}")
    public String showEdit(Model model,@PathVariable long taskId){
        if(!model.containsAttribute("taskBindingModel")){
            model.addAttribute("taskBindingModel", new TaskBindingModel());
            model.addAttribute("taskBindingModel", this.taskService.findById(taskId));
        }
        model.addAttribute("categories", taskService.getAllCategories());
        return "edit-task";

    }

    }


