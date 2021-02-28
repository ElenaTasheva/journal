package com.example.my_bullet_journal;

import com.example.my_bullet_journal.models.entities.Quote;
import com.example.my_bullet_journal.models.view.QuoteViewModel;
import com.example.my_bullet_journal.services.QuoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {


    private final QuoteService quoteService;

    public AppInitializer(QuoteService quoteService) {
        this.quoteService = quoteService;
    }


    @Override
    public void run(String... args) throws Exception {

        quoteService.save();


    }
}
