package com.shorterurl.shorterurl.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(jakarta.servlet.http.HttpServletRequest request, Model model) {
        Object status = request.getAttribute(jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
    
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorTitle", "404 Not Found");
                model.addAttribute("errorMessage", "The requested URL could not be found.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorTitle", "500 Internal Server Error");
                model.addAttribute("errorMessage", "An internal server error has occurred.");
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("errorTitle", "400 Bad Request");
                model.addAttribute("errorMessage", "The server could not understand the request due to invalid syntax.");
            } else {
                model.addAttribute("errorTitle", "Error " + statusCode);
                model.addAttribute("errorMessage", "An error has occurred. Error code: " + statusCode);
            }
        }

        return "error";
    }

    
}

