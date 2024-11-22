package com.entityManager.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	//Initializes a logger for this class to record exception details and debugging information.
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
	//Handle EmployeeNotFound exception
	@ExceptionHandler(EmployeeNotFoundException.class)
	public String employeeNotFoundException(EmployeeNotFoundException employeeNotFoundException, Model model) {
		model.addAttribute("errorMessage", employeeNotFoundException.getMessage());
		return "error";
	}
	//Handle UserNotFoundException
	@ExceptionHandler(UsernameNotFoundException.class)
	public String handleUserNotFoundException(UsernameNotFoundException exception, WebRequest request, Model model) {
		logger.error("Error: {}", exception.getMessage());
		model.addAttribute("error", "User Not Found!");
		model.addAttribute("message", exception.getMessage());
		model.addAttribute("details", request.getDescription(false));
		model.addAttribute("timestamp", LocalDateTime.now());
		return "error";//Thymeleaf template name
	}
	//For unhandled exceptions, forward users to the same error page.
	@ExceptionHandler(Exception.class)
	public String handleGlobalException(Exception ex, WebRequest request, Model model) {
	    logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
	    model.addAttribute("error", "Internal server error");
	    model.addAttribute("message", "An unexpected error occurred");
	    model.addAttribute("details", request.getDescription(false));
	    model.addAttribute("timestamp", LocalDateTime.now());
	    return "error"; // Thymeleaf template name
	}

}
