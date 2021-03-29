package com.example.my_bullet_journal.services;


import com.example.my_bullet_journal.models.bindings.QuoteBingingModel;
import com.example.my_bullet_journal.models.entities.Quote;
import com.example.my_bullet_journal.models.view.QuoteViewModel;
import com.example.my_bullet_journal.repositories.QuoteRepository;
import com.example.my_bullet_journal.services.impl.QuoteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;

public class QuoteServiceTest {


    private QuoteRepository mockedQuoteRepository;
    private QuoteBingingModel quoteBingingModel = new QuoteBingingModel();
    private ModelMapper modelMapper = new ModelMapper();


    @Before
    public void init () {
        quoteBingingModel.setAuthor("").setText("Testing quotes");
        this.mockedQuoteRepository = Mockito.mock(QuoteRepository.class);

    }

    @Test
    public void setRandomQuoteReturnsDotsWhenRepoIsEmpty() {

        QuoteViewModel quoteViewModel = new QuoteViewModel();
        quoteViewModel.setText("...");
        QuoteService quoteService = new QuoteServiceImpl(mockedQuoteRepository, modelMapper);
        Assert.assertEquals(quoteViewModel.getText(), quoteService.getRandomQuote().getText());

    }

    @Test
    public void setRandomQuoteReturnsQuoteFromRepoWhenRepoIsNotEmpty(){
        Quote quote1 = new Quote();
        Quote quote2 = new Quote();
        quote1.setText("test");
        quote2.setText("test2");
        Mockito.when(this.mockedQuoteRepository.count()).thenReturn((long) 2);
        Mockito.when(this.mockedQuoteRepository.findAll()).thenReturn(List.of(quote1, quote2));
        QuoteService quoteService = new QuoteServiceImpl(mockedQuoteRepository, modelMapper);
        Assert.assertNotEquals("...", quoteService.getRandomQuote().getText());
    }

    @Test
    public void addQuoteSetAnonymousAuthorIfAuthorIsNull() {

        QuoteService quoteService = new QuoteServiceImpl(mockedQuoteRepository, modelMapper);
        quoteService.add(quoteBingingModel);
        Assert.assertEquals("Anonymous", quoteBingingModel.getAuthor());
    }

    @Test
    public void addQuoteDoesNotSetAnonymousAuthorWhenAuthorIsPresent() {
        QuoteService quoteService = new QuoteServiceImpl(mockedQuoteRepository, modelMapper);
        quoteBingingModel.setAuthor("Author Present");
        quoteService.add(quoteBingingModel);
        Assert.assertNotEquals("Anonymous", quoteBingingModel.getAuthor());
    }
}
