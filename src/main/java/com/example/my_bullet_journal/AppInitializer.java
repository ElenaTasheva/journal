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

    public AppInitializer(QuoteService quoteService, RoleService roleService, ExpenseService expenseService, TopicService topicService, UserService userService) {
        this.quoteService = quoteService;
        this.roleService = roleService;

        this.topicService = topicService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {

        quoteService.save();
        roleService.seedRoles();
        topicService.seedTopics();
        userService.seedAdmin();

        //todo change the text in the paragrpahs for mistakes (add-expenses, add-income)


    }
}
