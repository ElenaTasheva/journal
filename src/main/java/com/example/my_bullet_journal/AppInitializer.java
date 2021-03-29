package com.example.my_bullet_journal;

import com.example.my_bullet_journal.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {


    private final QuoteService quoteService;
    private final RoleService roleService;
    private final TopicService topicService;
    private final UserService userService;
    private final TaskService taskService;


    public AppInitializer(QuoteService quoteService, RoleService roleService, TopicService topicService, UserService userService, TaskService taskService) {
        this.quoteService = quoteService;
        this.roleService = roleService;

        this.topicService = topicService;
        this.userService = userService;

        this.taskService = taskService;
    }


    @Override
    public void run(String... args) throws Exception {


        quoteService.save();
        roleService.seedRoles();
        topicService.seedTopics();
        userService.seedAdmin();

        // changing status if the server was off and the schedule task didnt work
        //taskService.changeTaskStatusWhenStartingTheApp();


        //todo change the text in the paragrphs for mistakes (add-expenses, add-income)
        //todo run procedures to check the balance and the status -- check
        //todo make custom Exception "resource not found";
        //todo add frontEnd\validation with registerForm
        //todo do aop for the errors - IllegalArgumentException


    
    }
}
