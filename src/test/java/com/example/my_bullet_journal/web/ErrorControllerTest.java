package com.example.my_bullet_journal.web;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class ErrorControllerTest {


    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    public void returnsView404WhenPageIsNotFound() throws Exception {
        JournalErrorController journalErrorController = new JournalErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(404);
        Assert.assertEquals(journalErrorController.showError(request), "error-404");


    }

    @Test
    public void returnsView403WhenThereIsAnError() throws Exception {
        JournalErrorController journalErrorController = new JournalErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(403);
        Assert.assertEquals(journalErrorController.showError(request), "error-403");


    }

    @Test
    public void returnsView500ForAnyOtherError() throws Exception {
        JournalErrorController journalErrorController = new JournalErrorController();
        Mockito.when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(400);
        Assert.assertEquals(journalErrorController.showError(request), "error-500");


    }

}
