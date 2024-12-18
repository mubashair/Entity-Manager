package com.entityManager.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entityManager.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String username);

}
