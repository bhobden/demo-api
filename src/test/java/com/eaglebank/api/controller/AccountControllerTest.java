package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.entity.account.AccountType;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest extends BaseITest {

    // Scenario: User wants to create a new bank account
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts endpoint with all the required data
    // Then a new bank account is created, and the account details are returned
    @Test
    void createAccount_shouldReturn201() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        CreateBankAccountRequest req = new CreateBankAccountRequest();
        req.setAccountName("Personal Bank Account");
        req.setAccountType(AccountType.PERSONAL);

        mockMvc.perform(post("/v1/accounts")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").isNotEmpty())
                .andExpect(jsonPath("$.accountName").value("Personal Bank Account"));
    }

    // Scenario: User wants to create a new bank account without supplying all required data
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts endpoint with required data missing
    // Then the system returns a Bad Request status code and error message
    @Test
    void createAccount_shouldReturn400ForInvalidRequest() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        CreateBankAccountRequest req = new CreateBankAccountRequest();
        // Missing required fields

        mockMvc.perform(post("/v1/accounts")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to view their bank accounts
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts endpoint
    // Then all the bank accounts associated with their userId are returned
    @Test
    void listAccounts_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);
        String accountNumber2 = createAccount(jwtToken);

        mockMvc.perform(get("/v1/accounts")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts[0].accountNumber").value(accountNumber))
                .andExpect(jsonPath("$.accounts[1].accountNumber").value(accountNumber2));
    }

    // Scenario: User wants to fetch their bank account details
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId} endpoint
    // And the account is associated with their userId
    // Then the system fetches the bank account details
    @Test
    void fetchAccount_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        mockMvc.perform(get("/v1/accounts/" + accountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber));
    }

    // Scenario: User wants to fetch another user's bank account details
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId} endpoint
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void fetchOtherUsersAccount_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));

        mockMvc.perform(get("/v1/accounts/" + otherAccountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId} endpoint
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void fetchNonExistentAccount_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/accounts/acc-notfound")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to update their bank account details
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/accounts/{accountId} endpoint supplying all the required data
    // And the account is associated with their userId
    // Then the system updates the bank account information and returns the updated data
    @Test
    void updateAccount_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        UpdateBankAccountRequest req = new UpdateBankAccountRequest();
        req.setAccountName("Updated Account Name");

        mockMvc.perform(patch("/v1/accounts/" + accountNumber)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName").value("Updated Account Name"));
    }

    // Scenario: User wants to update another user's bank account details
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/accounts/{accountId} endpoint
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void updateOtherUsersAccount_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));

        UpdateBankAccountRequest req = new UpdateBankAccountRequest();
        req.setAccountName("Updated Account Name");

        mockMvc.perform(patch("/v1/accounts/" + otherAccountNumber)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to update a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a PATCH request to the /v1/accounts/{accountId} endpoint
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void updateNonExistentAccount_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        UpdateBankAccountRequest req = new UpdateBankAccountRequest();
        req.setAccountName("Updated Account Name");

        mockMvc.perform(patch("/v1/accounts/acc-notfound")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User deletes an existing bank account
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/accounts/{accountId} endpoint
    // And the account is associated with their userId
    // Then the system deletes the bank account
    @Test
    void deleteAccount_shouldReturn204() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        mockMvc.perform(delete("/v1/accounts/" + accountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isNoContent());
    }

    // Scenario: User wants to delete another user's bank account details
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/accounts/{accountId} endpoint
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void deleteOtherUsersAccount_shouldReturn403() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));

        mockMvc.perform(delete("/v1/accounts/" + otherAccountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to delete a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a DELETE request to the /v1/accounts/{accountId} endpoint
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void deleteNonExistentAccount_shouldReturn404() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(delete("/v1/accounts/acc-notfound")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to create a bank account without authentication
    // Given a user is not authenticated
    // When the user makes a POST request to the /v1/accounts endpoint
    // Then the system returns a Forbidden status code
    @Test
    void createAccount_shouldReturn401IfNoAuth() throws Exception {
        CreateBankAccountRequest req = new CreateBankAccountRequest();
        req.setAccountName("Personal Bank Account");
        req.setAccountType(AccountType.PERSONAL);

        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }
}