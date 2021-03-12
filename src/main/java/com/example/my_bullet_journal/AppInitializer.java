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
        //todo why the links from the admin panel are not secured and the normal users have access to the url
        //todo run procedures to check the balance and the status
        //todo task procedure to check tasks - date
        //todo fix comment service model
        //todo try to  fix edit with modelAndView
    }
}
