package com.example.EmployeeManagementApplication.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public RedirectView handleEmailExists(EmailAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        System.out.println("EmailAlreadyExistsException caught: " + ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return new RedirectView("/employees");
    }

    @ExceptionHandler(Exception.class)
    public Object handleOtherExceptions(Exception ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String uri = request.getRequestURI();

        // If request is for API docs or swagger UI, rethrow or handle differently
        if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui") || uri.startsWith("/swagger-ui.html")) {
            // Return raw error or JSON response, do NOT redirect
            return new ResponseEntity<>("API Documentation error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // For UI requests, redirect as before
        redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
        String referer = request.getHeader("Referer");
        return new RedirectView(referer != null ? referer : "/employees");
    }

}



