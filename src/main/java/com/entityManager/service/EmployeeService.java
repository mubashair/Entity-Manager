package com.entityManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entityManager.model.Employee;
import com.entityManager.repo.EmployeeRepo;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepo employeeRepo;
	
	public void saveEmployee(Employee employee) {
		employeeRepo.save(employee);
	}
}
