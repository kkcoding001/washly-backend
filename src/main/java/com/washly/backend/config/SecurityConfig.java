
package com.washly.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.washly.backend.security.JwtAuthenticationFilter;


/**
 * Security configuration for the application.
 *
 * Configures:
 * 1. JWT-based authentication
 * 2. Stateless session management
 * 3. Custom error responses for authentication/authorization failures
 * 4. URL access rules
 */


@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable()) // Disable CSRF because this API uses JWT tokens
        .formLogin(form -> form.disable()) // Disable default login form since authentication
        .httpBasic(basic -> basic.disable()) // Disable HTTP Basic Authentication
        
        // Configure application as stateless
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        
        // Handle authentication and authorization exceptions
        .exceptionHandling(exceptions -> exceptions
        		
        		// Called when a user is not authenticated
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "{\"message\":\"Authentication required\"}");
                })
                
             // Called when an authenticated user lacks permission
                .accessDeniedHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "{\"message\":\"Access denied\"}");
                }))
        // Configure endpoint authorization rules
        .authorizeHttpRequests(auth -> auth
        		// Allow all requests to authentication endpoints login, register, refresh-token
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN") // checks for the authority ROLE_ADMIN
            .requestMatchers("/api/staff/**") 
                .hasAnyRole("STAFF", "ADMIN") // allows either staff or an administrator
            .requestMatchers("/api/customer/**") 
                .hasAnyRole("CUSTOMER", "ADMIN") // allows customers or administrators
            .anyRequest().authenticated() // All other endpoints require authentication
        )
        .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}
