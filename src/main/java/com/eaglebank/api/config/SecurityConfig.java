package com.eaglebank.api.config;

import com.eaglebank.api.security.JwtAuthenticationFilter;
import com.eaglebank.api.security.JwtUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures Spring Security for stateless JWT authentication.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Configure the filter chain:
     * - disable CSRF
     * - allow unauthenticated access to /api/login
     * - ensure all other endpoints require auth
     * - register JWT filter before UsernamePasswordAuthenticationFilter
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection for stateless APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/v1/users", "/v1/login").permitAll() 
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow public access to options
                .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs.yaml").permitAll() // Allow public access to login
                .anyRequest().authenticated()              // Require auth for other routes
            )
            .sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * BCrypt is a strong password hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the default AuthenticationManager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
