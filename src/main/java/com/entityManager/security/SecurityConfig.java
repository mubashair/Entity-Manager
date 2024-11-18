package com.entityManager.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.entityManager.model.User;
import com.entityManager.repo.UserRepo;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/error", "register").permitAll() // Public access
                .requestMatchers("/employees").hasAnyRole("USER", "ADMIN") // USER and ADMIN can view employees
                .requestMatchers("/employees/new", "/employees/edit/**", "/employees/delete/**").hasRole("ADMIN") // Only ADMIN can create, edit, delete
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
        /*UserDetails user = User.builder()
            .username("test")
            .password(passwordEncoder().encode("test")) // Encode password
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin")) // Encode password
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/

    
    	
    	
    	
    	
    	//logic to get user from database
    	return username->{
    		User user1  = userRepo.findByUsername(username);
    		
    		if(user1 == null) {
    			throw  new UsernameNotFoundException("User Not Found");
    		}
    		return new org.springframework.security.core.userdetails.User(
    				user1.getUsername(), user1.getPassword(), AuthorityUtils.createAuthorityList("ROLE_"+ user1.getRole())
    				);
    	};
   }

    @Bean
    public CommandLineRunner initAdminUser(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin already exists
            if (userRepository.findByUsername("admin") == null) {
                // Create admin user
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("adminpassword")); // Set a secure password
                adminUser.setRole("ADMIN");
                
                userRepository.save(adminUser);
                System.out.println("Admin user created: username='admin', password='adminpassword'");
            }
        };
    }
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Strong password encoding for production
    }
}
