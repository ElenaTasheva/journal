package com.example.my_bullet_journal.web;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class JournalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String showError(HttpServletRequest request) {

        Object status =
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                return "error-404";


            }

        }
        return "error-404";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
