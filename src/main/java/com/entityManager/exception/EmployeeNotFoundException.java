package com.entityManager.exception;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(Long id) {
		super("Employee with ID "+ id +" Not Found");
	}
	
	

}
