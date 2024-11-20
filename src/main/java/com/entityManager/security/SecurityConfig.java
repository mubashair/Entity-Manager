package com.entityManager.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.entityManager.model.User;
import com.entityManager.repo.UserRepo;



@Configuration
public class SecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	public static final String ROLE_USER = "USER";
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final String[] PUBLIC_END_POINTS = {"/", "/login", "/error", "/register"};
	//public static final String[] RESTRICTED_END_POINTS = {"/employees"};
	public static final String [] ADMIN_END_POINTS = {"/employees/new", "/employees/edit/**", "/employees/delete/**"};
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_END_POINTS).permitAll() // Public access
                .requestMatchers("/employees").hasAnyRole("USER", "ADMIN") // USER and ADMIN can view employees
                .requestMatchers( ADMIN_END_POINTS).hasRole("ADMIN") // Only ADMIN can create, edit, delete
                
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/employees", true)
                .permitAll() 
            )
            .logout(logout -> logout
            		.logoutUrl("/logout") // Custom logout URL
                    .logoutSuccessUrl("/login?logout") // Redirect after logout
                    .invalidateHttpSession(true) // Invalidate session
                    .deleteCookies("JSESSIONID") // Remove session cookies
                    .permitAll()
            );
            //.csrf(); // Disable CSRF for development; enable in production

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo ) {
    	//logic to get user from database
    	return username->{
    		User user1  = userRepo.findByUsername(username);
    		
    		if(user1 == null) {
    			throw  new UsernameNotFoundException("User Not Found");
    		}
    		return new org.springframework.security.core.userdetails.User(
    				user1.getUsername(), 
    				user1.getPassword(), 
    				Collections.singletonList(new SimpleGrantedAuthority(toSpringRole(user1.getRole())))
    				);
    	};
   }
    private String toSpringRole(String role) {
    	return "ROLE_"+role;
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin already exists
            if (userRepository.findByUsername("admin") == null) {
                // Create admin user
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode(System.getenv("ADMIN_PASSWORD") != null ? System.getenv("ADMIN_PASSWORD") : "adminpass")); // Set a secure password
                adminUser.setRole(ROLE_ADMIN);
                
                userRepository.save(adminUser);
                if (System.getenv("ADMIN_PASSWORD") == null) {
                    logger.warn("ADMIN_PASSWORD environment variable not set. Using default password.");
                }

                logger.info("Admin user created: username='admin', password='adminpassword'");
            }
        };
    }
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Strong password encoding for production
    }
}
