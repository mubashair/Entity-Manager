package com.entityManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/error").permitAll() // Public access
                .requestMatchers("/employees").hasAnyRole("USER", "ADMIN") // USER and ADMIN can view employees
                .requestMatchers("/employees/new", "/employees/edit/**", "/employees/delete/**").hasRole("ADMIN") // Only ADMIN can create, edit, delete
            )
            .formLogin(form -> form
                //.loginPage("/login") // Custom login page
                //.defaultSuccessUrl("/employees", true)
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for development; enable in production

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
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
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Strong password encoding for production
    }
}
