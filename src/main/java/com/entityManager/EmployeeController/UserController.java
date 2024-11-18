package com.entityManager.EmployeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//This controller will display the registration form and handle form submissions.
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.entityManager.model.User;
import com.entityManager.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";//This maps to login.html
	}
	
	@GetMapping("/register")
	public String showRegisterationForm(Model model) {
		model.addAttribute("newUser", new User());
		return "register";// Points to src/main/resources/templates/register.html
	}
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, Model model) {
		
		boolean isRegistered = userService.registerNewUser(user);
		if(!isRegistered) {
			model.addAttribute("error", "Username already exist please choose different username");
			return "/register";
		}
		
		//userService.registerNewUser(user);
		//model.addAttribute("message", "Reistration successful your can log in!");
		return "redirect:/login?registered=true"; // Redirect to login page after successful registration
		
	}
	

}