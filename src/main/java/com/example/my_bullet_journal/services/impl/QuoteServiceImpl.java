package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.bindings.QuoteBingingModel;
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
    private Quote quote;


    public QuoteServiceImpl(QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public void save() throws IOException{
        if(quoteRepository.count()==0) {
            List<String> tokens = new ArrayList<>(Files.readAllLines(Path.of(PATH)));
            for (String token : tokens) {
                String[] quote = token.split(" ~");
                Quote quote1 = new Quote(quote[0], quote[1]);
                quoteRepository.save(quote1);
            }
        }
        setQuote();

    }

    private void setQuote() {
        quote = getRandom();
    }

    @Override
    public QuoteViewModel getRandomQuote(){
        if(quote == null){
            setQuote();
        }
        return this.modelMapper.map(quote, QuoteViewModel.class);
    }

    @Override
    public void add(QuoteBingingModel quoteBindingModel) {
        if(quoteBindingModel.getAuthor().trim().isEmpty()){
            quoteBindingModel.setAuthor("Anonymous");
        }
        quoteRepository.save(this.modelMapper.map(quoteBindingModel, Quote.class));
    }

    private Quote getRandom() {
        if(quoteRepository.count() == 0){
            return new Quote("...", "...");
        }
        Random random = new Random();
        List<Quote> quotes = this.quoteRepository.findAll();
        int index =  random.nextInt(quotes.size() - 1) + 1;
        System.out.println();
        return quotes.get(index);
    }
}
