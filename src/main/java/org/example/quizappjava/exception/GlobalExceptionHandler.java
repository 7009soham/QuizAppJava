package org.example.quizappjava.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        ex.printStackTrace(); // Print to console/log
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // fallback JSP
    }
}
