/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author 123
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection
                .headers()
                .frameOptions().sameOrigin() // Prevent clickjacking
                .and()
                .authorizeHttpRequests()
                // Role-based access control
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Only ADMIN can access /api/admin/**
                .requestMatchers("/api/books/**", "/api/patrons/**", "/api/borrow/**").hasAnyRole("LIBRARIAN", "ADMIN") // LIBRARIAN or ADMIN can access these endpoints
                .requestMatchers("/api/public/**").permitAll() // Public access
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
                .httpBasic(); // Use HTTP Basic Authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use a secure password encoder
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create users with different roles
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123")) // Encode password
                .roles("ADMIN") // Assign ADMIN role
                .build();

        UserDetails librarian = User.builder()
                .username("librarian")
                .password(passwordEncoder().encode("librarian123")) // Encode password
                .roles("LIBRARIAN") // Assign LIBRARIAN role
                .build();

        UserDetails member = User.builder()
                .username("member")
                .password(passwordEncoder().encode("member123")) // Encode password
                .roles("MEMBER") // Assign MEMBER role
                .build();

        // Store users in an in-memory user store
        return new InMemoryUserDetailsManager(admin, librarian, member);
    }
}
