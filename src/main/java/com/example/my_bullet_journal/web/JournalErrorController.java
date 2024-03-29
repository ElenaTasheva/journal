package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.web.annotations.PageTitle;
import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class JournalErrorController implements ErrorController {

    @RequestMapping("/error")
    @PageTitle("Error Page")
    public String showError(HttpServletRequest request) {

        Object status =
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                return "error-404";
            }
            else if (statusCode == HttpStatus.SC_FORBIDDEN) {
                return "error-403";
            }

        }
               return "error-500";

    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
