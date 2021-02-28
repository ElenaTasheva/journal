package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.view.QuoteViewModel;

import java.io.IOException;

public interface QuoteService {


    void save() throws IOException;
    QuoteViewModel getRandomQuote();
}
