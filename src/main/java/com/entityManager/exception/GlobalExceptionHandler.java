package com.entityManager.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(EmployeeNotFoundException.class)
	public String employeeNotFoundException(EmployeeNotFoundException employeeNotFoundException, Model model) {
		model.addAttribute("errorMessage", employeeNotFoundException.getMessage());
		return "error";
	}
}
