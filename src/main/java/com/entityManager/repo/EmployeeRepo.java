package com.entityManager.repo;

import org.springframework.stereotype.Repository;

import com.entityManager.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeRepo {
	@PersistenceContext
	private EntityManager entityManager;
	//method to save a new employee
	@Transactional
	public void save(Employee employee) {
		entityManager.persist(employee);
	}
	//
	
	
}
