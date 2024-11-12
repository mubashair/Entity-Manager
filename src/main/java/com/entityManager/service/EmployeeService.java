package com.entityManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	// Get an employee by ID
	public Employee getEmployeeById(Long id) {
		return entityManager.find(Employee.class, id);
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
	public void deleteEmployee(Long id) {
		Employee employee = getEmployeeById(id);
		if( employee != null) {
			entityManager.remove(employee);
		}
		
	}
	
	
}
