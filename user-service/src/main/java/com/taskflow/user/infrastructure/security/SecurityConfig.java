package com.taskflow.user.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Instances of JwtUtil and UserDetailsService injected by Spring
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Constructor for dependency injection of JwtUtil and UserDetailsService
    @Autowired
    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Security filter configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Creates an instance of JwtRequestFilter with JwtUtil and UserDetailsService
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil, userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for REST APIs (not applicable to stateless APIs)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/login").permitAll() // Allows access to the login endpoint without authentication
                        .requestMatchers(HttpMethod.GET, "/auth").permitAll() // Allows access to GET /auth endpoint without authentication
                        .requestMatchers(HttpMethod.POST, "/user").permitAll() // Allows access to POST /user endpoint without authentication
                        .requestMatchers("/user/**").authenticated() // Requires authentication for any endpoint starting with /user/
                        .anyRequest().authenticated() // Requires authentication for all other requests
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sets session policy to stateless
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Adds the JWT filter before the default authentication filter

        // Returns the configured security filter chain
        return http.build();
    }

    // Configures the PasswordEncoder to encrypt passwords using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Returns a BCryptPasswordEncoder instance
    }

    // Configures the AuthenticationManager using AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Obtains and returns the AuthenticationManager from the authentication configuration
        return authenticationConfiguration.getAuthenticationManager();
    }

}
