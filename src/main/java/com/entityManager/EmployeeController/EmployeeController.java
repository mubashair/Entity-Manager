package com.entityManager.EmployeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entityManager.service.EmployeeService;

import jakarta.persistence.EntityManager;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	//Display the list of employees
	@GetMapping
	public String listEmployees(Model model) {
		model.addAttribute("listofemployees", employeeService.getAllEmployees());
		return "listEmployees";
	}
	// Get an employee by ID
	public Employee getEmployeeById() {
		
	}
}
