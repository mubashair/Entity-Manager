package com.entityManager.EmployeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entityManager.model.Employee;
import com.entityManager.service.EmployeeService;

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
	// Display the form to create a new employee
	@GetMapping("/new")
	public String  newEmployeeForm(Model model) {
		model.addAttribute("emp", new Employee());
		return "employee_form";
	}
	//Handle form submission for creating or updating an employee
	@PostMapping
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
		
	}
	//Display the form to edit an existing employee
	@GetMapping("/edit/{id}")
	public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("emp", employeeService.getEmployeeById(id));
		
		return "employee_form";
		
	}
	//Handle employee deletion
	@GetMapping("delete/{id}")
	public String deleteEmployee(@PathVariable("id") Long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}
	
}
