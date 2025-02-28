/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
            .csrf().disable() // Disable CSRF for simplicity (use with caution)
            .headers()
                .frameOptions().sameOrigin() // Set X-Frame-Options to SAMEORIGIN
            .and()
            .authorizeHttpRequests() // Configure authorization rules
                .requestMatchers("/api/books/**", "/api/patrons/**", "/api/borrow/**").authenticated() // Secure these endpoints
                .anyRequest().permitAll() // Allow public access to all other endpoints
            .and()
            .httpBasic(); // Enable Basic Authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create an in-memory user for testing
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("librarian")
            .password("password")
            .roles("LIBRARIAN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
