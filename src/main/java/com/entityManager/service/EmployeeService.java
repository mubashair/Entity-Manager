package com.entityManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.entityManager.exception.EmployeeNotFoundException;
import com.entityManager.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	@PersistenceContext
	EntityManager entityManager;
	
	//Get all employees
	public List<Employee> getAllEmployees(){
		TypedQuery<Employee> typedQuery= entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
		return typedQuery.getResultList();
	}
	// Get an employee by ID throwing an exception if employee is not found
	public Optional<Employee> getEmployeeById(Long id) {
		return Optional.ofNullable(entityManager.find(Employee.class, id));
	}
	//save or update an employee
	@Transactional
	public void saveEmployee(Employee employee) {
		if(employee == null) {
			entityManager.persist(employee);//create a new emp
		}else { 
			entityManager.merge(employee);//update an existing emp
		}
	}
	//delete an emp
	@Transactional
	public void deleteEmployeeById(Long id) {
		Employee employee = entityManager.find(Employee.class, id);
		if( employee != null) {
			entityManager.remove(employee);
		}
		
	}
	
	
}
