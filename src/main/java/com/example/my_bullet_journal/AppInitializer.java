package com.example.my_bullet_journal;

import com.example.my_bullet_journal.services.QuoteService;
import com.example.my_bullet_journal.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {


    private final QuoteService quoteService;
    private final RoleService roleService;

    public AppInitializer(QuoteService quoteService, RoleService roleService) {
        this.quoteService = quoteService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {

        quoteService.save();
        roleService.seedRoles();



    }
}
