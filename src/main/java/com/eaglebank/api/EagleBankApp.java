package com.eaglebank.api;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.model.entity.bankaccount.AccountType;
import com.eaglebank.api.model.entity.user.address.Address;
import com.eaglebank.api.service.AccountService;
import com.eaglebank.api.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * EagleBankApp is the entry point for the EagleBank Spring Boot application.
 * <p>
 * This class bootstraps the application and provides a {@link CommandLineRunner}
 * bean to initialize a test user and two bank accounts in the database at startup
 * if none exist. This is useful for development and testing purposes.
 * </p>
 *
 * <h2>Startup Behavior</h2>
 * <ul>
 *   <li>Creates a test user with a sample address, phone number, email, and password.</li>
 *   <li>Creates a savings account and a personal account for the test user.</li>
 *   <li>Prints the created user and account details to the console.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 *   $ ./mvnw spring-boot:run
 * </pre>
 */
@OpenAPIDefinition(
    info = @Info(
        title = "EagleBank API",
        version = "1.0.0",
        description = "Banking endpoints for user, account, and transaction flows"
    )
)
@SpringBootApplication
public class EagleBankApp {

    /**
     * Main method to launch the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(EagleBankApp.class, args);
    }

    /**
     * Initializes a test user and two bank accounts at application startup.
     * This bean runs after the application context is loaded.
     *
     * @param userService    The UserService for user operations.
     * @param accountService The AccountService for account operations.
     * @return CommandLineRunner that performs the initialization.
     */
    @Bean
    CommandLineRunner init(UserService userService, AccountService accountService) {
        return args -> {
            // Create a sample address
            Address address = new Address();
            address.setLine1("123 Main St");
            address.setTown("Springfield");
            address.setPostcode("12345");
            address.setCounty("essex"); 
            
            // Create a test user
            CreateUserRequest user = new CreateUserRequest();
            user.setName("Test User");
            user.setAddress(address);
            user.setPhoneNumber("+353871234567");
            user.setEmail("test@eaglebank.com"); 
            user.setPassword("password123");
            
            UserResponse newUser = userService.createUser(user);

            System.out.println("Test user created with id: " + newUser.getId());
            System.out.println("Test user created with password: " + user.getPassword());

            // Create a savings account for the test user
            CreateBankAccountRequest newAccount1 = new CreateBankAccountRequest();
            newAccount1.setAccountName("Savings Account");
            newAccount1.setAccountType(AccountType.SAVINGS);
            accountService.createAccountForUser(newAccount1, newUser.getId());

            // Create a personal account for the test user
            CreateBankAccountRequest newAccount2 = new CreateBankAccountRequest();
            newAccount2.setAccountName("Personal Account");
            newAccount2.setAccountType(AccountType.PERSONAL);
            accountService.createAccountForUser(newAccount2, newUser.getId());
        };
    }
}
