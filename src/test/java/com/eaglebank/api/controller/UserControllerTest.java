package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.request.UpdateUserRequest;
import com.eaglebank.api.model.entity.user.address.Address;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends BaseITest {

    // Scenario: Create a new user
    // Given a user wants to signup for Eagle Bank
    // When the user makes a POST request to the /v1/users endpoint with all the required data
    // Then a new user is created
    @Test
    void createUser_shouldReturn201() throws Exception {
        Address address = new Address();
        address.setLine1(LINE1);
        address.setTown(TOWN);
        address.setPostcode(POSTCODE);
        address.setCounty(COUNTY);

        CreateUserRequest user = new CreateUserRequest();
        user.setName(NAME);
        user.setAddress(address);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    // Scenario: Create a new user without supplying all required data
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/users endpoint with required data missing
    // Then the system returns a Bad Request status code and error message
    @Test
    void createUser_shouldReturn400ForMissingData() throws Exception {
        CreateUserRequest user = new CreateUserRequest();
        // Missing required fields

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch their user details
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/users/{userId} endpoint supplying their userId
    // Then the system fetches the user details
    @Test
    void fetchUser_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/users/" + userName)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userName));
    }

    // Scenario: User wants to fetch the user details of another user
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/users/{userId} endpoint supplying another user's userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void fetchOtherUser_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/users/" + otherUser)
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch the user details of a non-existent user
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/users/{userId} endpoint supplying a userId which doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void fetchNonExistentUser_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/users/nonexistent")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to update their user details
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/users/{userId} endpoint supplying their userId and all the required data
    // Then the system updates the user details and returns the updated data
    @Test
    void updateUser_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        UpdateUserRequest req = new UpdateUserRequest();
        req.setName("Updated User");
        // ...set other fields...

        mockMvc.perform(patch("/v1/users/" + userName)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    // Scenario: User wants to update the user details of another user
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/users/{userId} endpoint supplying another user's userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void updateOtherUser_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);

        UpdateUserRequest req = new UpdateUserRequest();
        req.setName("Updated User");

        mockMvc.perform(patch("/v1/users/" + otherUser)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to update the user details of a non-existent user
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/users/{userId} endpoint supplying a userId which doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void updateNonExistentUser_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        UpdateUserRequest req = new UpdateUserRequest();
        req.setName("Updated User");

        mockMvc.perform(patch("/v1/users/nonexistent")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to delete their user details
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/users/{userId} endpoint
    // And they do not have a bank account
    // Then the system deletes their user
    @Test
    void deleteUser_shouldReturn204() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(delete("/v1/users/" + userName)
                .header("Authorization", jwtToken))
                .andExpect(status().isNoContent());
    }

    // Scenario: User wants to delete their user details and they have a bank account
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/users/{userId} endpoint
    // And they have a bank account
    // Then the system returns a Conflict status code and error message
    @Test
    void deleteUserWithAccount_shouldReturn409() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        createAccount(jwtToken);

        mockMvc.perform(delete("/v1/users/" + userName)
                .header("Authorization", jwtToken))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to delete user details of another user
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/users/{userId} endpoint
    // And the userId is associated with another user
    // Then the system returns a Forbidden status code and error message
    @Test
    void deleteOtherUser_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(delete("/v1/users/" + otherUser)
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to delete user details of a non-existent user
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/users/{userId} endpoint
    // And the userId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void deleteNonExistentUser_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(delete("/v1/users/nonexistent")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}