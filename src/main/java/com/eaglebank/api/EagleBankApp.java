package com.eaglebank.api;

import com.eaglebank.api.model.address.Address;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the Spring Boot application.
 */
@SpringBootApplication
public class EagleBankApp {

    public static void main(String[] args) {
        SpringApplication.run(EagleBankApp.class, args);
    }

    /**
     * Initialises a test user in the database at startup if none exist.
     */
    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            Address address = new Address();
            address.setLine1("123 Main St");
            address.setTown("Springfield");
            address.setPostcode("12345");
            address.setCounty("essex"); 
            
            CreateUserRequest user = new CreateUserRequest();
            user.setName("Test User");
            user.setAddress(address);
            user.setPhoneNumber("1234567890");
            user.setEmail("test@eaglebank.com"); 
            user.setPassword("password123");
            
            UserResponse newUser = userService.createUser(user);

            System.out.println("Test user created with id: " + newUser.getId());
            System.out.println("Test user created with password: " + user.getPassword());
        };
    }
}
