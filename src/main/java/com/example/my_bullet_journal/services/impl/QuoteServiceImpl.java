package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.Quote;
import com.example.my_bullet_journal.models.view.QuoteViewModel;
import com.example.my_bullet_journal.repositories.QuoteRepository;
import com.example.my_bullet_journal.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuoteServiceImpl implements QuoteService {


    private final QuoteRepository quoteRepository;
    private final String PATH = "src/main/resources/static/files/inspirational";
    private final ModelMapper modelMapper;
    private final List<Quote> quotes;

    public QuoteServiceImpl(QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
        quotes = this.quoteRepository.findAll();
    }

    @Override
    public void save() throws IOException {
        if(quoteRepository.count()==0) {
            List<String> tokens = new ArrayList<>(Files.readAllLines(Path.of(PATH)));
            for (String token : tokens) {
                String[] quote = token.split(" ~");
                Quote quote1 = new Quote(quote[0], quote[1]);
                quoteRepository.save(quote1);
            }
        }

    }

    @Override
    public QuoteViewModel getRandomQuote() {
        Quote quote = random();
        return this.modelMapper.map(quote, QuoteViewModel.class);
    }

    private Quote random() {
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size() -1) + 1);
    }
}
