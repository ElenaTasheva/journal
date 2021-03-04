package com.example.my_bullet_journal;

import com.example.my_bullet_journal.services.ExpenseService;
import com.example.my_bullet_journal.services.QuoteService;
import com.example.my_bullet_journal.services.RoleService;
import com.example.my_bullet_journal.services.TopicService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {


    private final QuoteService quoteService;
    private final RoleService roleService;
    private final TopicService topicService;

    public AppInitializer(QuoteService quoteService, RoleService roleService, ExpenseService expenseService, TopicService topicService) {
        this.quoteService = quoteService;
        this.roleService = roleService;

        this.topicService = topicService;
    }


    @Override
    public void run(String... args) throws Exception {

        quoteService.save();
        roleService.seedRoles();
        topicService.seedTopics();



    }
}
