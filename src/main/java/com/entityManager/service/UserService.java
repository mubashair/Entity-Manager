package com.entityManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entityManager.model.User;
import com.entityManager.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean registerNewUser(User user) {
		//check if username already exist
		if(userRepo.findByUsername(user.getUsername()) != null) {
			return false;//user already exist
		}
		
		//encode the user's password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//assign the default Role
		user.setRole("USER");
		//save the user in the database
		userRepo.save(user);
		return true;
	}

}
