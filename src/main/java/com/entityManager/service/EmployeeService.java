package com.entityManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entityManager.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class EmployeeService {
	@PersistenceContext
	EntityManager entityManager;
	
	//Get all employees
	public List<Employee> getAllEmployees(){
		TypedQuery<Employee> typedQuery= entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
		return typedQuery.getResultList();
	}
	
	
}
