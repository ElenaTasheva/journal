package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.bindings.QuoteBingingModel;
import com.example.my_bullet_journal.models.view.QuoteViewModel;
import org.apache.http.HttpException;

import java.io.IOException;

public interface QuoteService {


    void save() throws IOException;
    QuoteViewModel getRandomQuote();

    void add(QuoteBingingModel quoteBindingModel);
}
