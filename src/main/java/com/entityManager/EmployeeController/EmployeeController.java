package com.entityManager.EmployeeController;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entityManager.exception.EmployeeNotFoundException;
import com.entityManager.model.Employee;
import com.entityManager.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	//Incorporate logging to help debug issues and trace operations. Use the Logger from SLF4J for this purpose
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	//Switch to constructor-based dependency injection to make the controller easier to test and 
	//avoid potential issues with uninitialized beans.
	private EmployeeService employeeService;
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	//Display the list of employees
	@GetMapping
	public String listEmployees(Model model) {
		logger.info("Fetching all employees");
		model.addAttribute("listofemployees", employeeService.getAllEmployees());//Fetch employees
		return "listEmployees";
	}
	// Display the form to create a new employee
	@GetMapping("/new")
	public String  newEmployeeForm(Model model) {
		model.addAttribute("emp", new Employee());
		return "employee_form";
	}
	//Handle form submission for creating or updating an employee
	@PostMapping
	public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
		
	}
	//Display the form to edit an existing employee
	@GetMapping("/edit/{id}")
	public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
		logger.info("Fetching employee with ID: {}", id);
		Optional<Employee> employee= employeeService.getEmployeeById(id);
		if(employee.isPresent()) {
			model.addAttribute("emp", employee.get());
			return "employee_form";
		}else {
			logger.error("Employee with ID {} not found", id);
			throw new EmployeeNotFoundException(id);
		}
		
		
		
		
	}
	//Handle employee deletion
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") Long id) {
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		if(employee.isPresent()) {
			employeeService.deleteEmployeeById(id);
			return "redirect:/employees";
		}else {
			throw new EmployeeNotFoundException(id);
		}
		
		
	}
	
}
