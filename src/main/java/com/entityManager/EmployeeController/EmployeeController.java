package com.entityManager.EmployeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entityManager.model.Employee;
import com.entityManager.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@PostMapping
	public String addEmployee(@RequestBody Employee employee ) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
	}

}
