package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.entity.bankaccount.Currency;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest extends BaseITest {

    // Scenario: User wants to deposit money into their bank account
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with all the required data
    // And the transaction type is deposit
    // And the account is associated with their userId
    // Then the deposit is registered against the account and the account balance is updated
    @Test
    void depositTransaction_shouldReturn201AndUpdateBalance() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(100.0);
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.DEPOSIT);

        mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(100.0));

        // Fetch account and check balance updated
        mockMvc.perform(get("/v1/accounts/" + accountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    // Scenario: User wants to withdraw money from their bank account
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with all the required data
    // And the transaction type is withdrawal
    // And the account has sufficient funds
    // And the account is associated with their userId
    // Then the withdrawal is registered against the account and the account balance is updated
    @Test
    void withdrawalTransaction_shouldReturn201AndUpdateBalance() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        // Deposit first to ensure sufficient funds
        createTransaction(accountNumber, jwtToken, 200);

        CreateTransactionRequest withdrawal = new CreateTransactionRequest();
        withdrawal.setAmount(100.0);
        withdrawal.setCurrency(Currency.GBP);
        withdrawal.setType(TransactionType.WITHDRAWAL);

        mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(100.0));

        // Fetch account and check balance updated
        mockMvc.perform(get("/v1/accounts/" + accountNumber)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    // Scenario: User wants to withdraw money from their bank account but they have insufficient funds
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with all the required data
    // And the transaction type is withdrawal
    // And the account has insufficient funds
    // And the account is associated with their userId
    // Then the system returns a Unprocessable Entity status code and error message
    @Test
    void withdrawalTransaction_shouldReturn422IfInsufficientFunds() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(100.99); // Over the allowed maximum
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.WITHDRAWAL);

        mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to deposit or withdraw money into another user's bank account
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with all the required data
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void transaction_shouldReturn403IfForbidden() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));

        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(100.0);
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.DEPOSIT);

        mockMvc.perform(post("/v1/accounts/" + otherAccountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to deposit or withdraw money into a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with all the required data
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void transaction_shouldReturn404IfAccountNotFound() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(100.0);
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.DEPOSIT);

        mockMvc.perform(post("/v1/accounts/acc-notfound/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to deposit or withdraw money without supplying all required data
    // Given a user has successfully authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint with required data missing
    // Then the system returns a Bad Request status code and error message
    @Test
    void transaction_shouldReturn400ForInvalidRequest() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        CreateTransactionRequest req = new CreateTransactionRequest();
        // Missing required fields

        mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to view all transactions on their bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions endpoint
    // And the account is associated with their userId
    // Then the transactions are returned
    @Test
    void listTransactions_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);
        String transactionId = createTransaction(accountNumber, jwtToken);

        mockMvc.perform(get("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions[0].id").value(transactionId));
    }

    // Scenario: User wants to view all transactions on another user's bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions endpoint
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void listTransactions_shouldReturn403IfForbidden() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));

        mockMvc.perform(get("/v1/accounts/" + otherAccountNumber + "/transactions")
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to view all transactions on a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions endpoint
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void listTransactions_shouldReturn404IfAccountNotFound() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/accounts/acc-notfound/transactions")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch a transaction on their bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions/{transactionId} endpoint
    // And the account is associated with their userId
    // And the transactionId is associated with the accountId specified
    // Then the transaction details are returned
    @Test
    void fetchTransaction_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);
        String transactionId = createTransaction(accountNumber, jwtToken);

        mockMvc.perform(get("/v1/accounts/" + accountNumber + "/transactions/" + transactionId)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionId));
    }

    // Scenario: User wants to fetch a transaction on another user's bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions/{transactionId} endpoint
    // And the account is not associated with their userId
    // Then the system returns a Forbidden status code and error message
    @Test
    void fetchTransaction_shouldReturn403IfForbidden() throws Exception {
        String userName = createUser();
        String otherUser = createUser();
        String jwtToken = loginUser(userName);
        String otherAccountNumber = createAccount(loginUser(otherUser));
        String transactionId = createTransaction(otherAccountNumber, loginUser(otherUser));

        mockMvc.perform(get("/v1/accounts/" + otherAccountNumber + "/transactions/" + transactionId)
                .header("Authorization", jwtToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch a transaction on a non-existent bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions/{transactionId} endpoint
    // And the accountId doesn't exist
    // Then the system returns a Not Found status code and error message
    @Test
    void fetchTransaction_shouldReturn404IfAccountNotFound() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/accounts/acc-notfound/transactions/tan-12345")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch a transactions on a non-existent transaction ID
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions/{transactionId} endpoint
    // And the account is associated with their userId
    // And the transactionId does not exist
    // Then the system returns a Not Found status code and error message
    @Test
    void fetchTransaction_shouldReturn404IfTransactionNotFound() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        mockMvc.perform(get("/v1/accounts/" + accountNumber + "/transactions/tan-notfound")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User wants to fetch a transaction against the wrong bank account
    // Given a user has successfully authenticated
    // When the user makes a GET request to the /v1/accounts/{accountId}/transactions/{transactionId} endpoint
    // And the account is associated with their userId
    // And the transactionId is not associated with the accountId specified
    // Then the system returns a Not Found status code and error message
    @Test
    void fetchTransaction_shouldReturn404IfTransactionNotForAccount() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber1 = createAccount(jwtToken);
        String accountNumber2 = createAccount(jwtToken);
        String transactionId = createTransaction(accountNumber1, jwtToken);

        mockMvc.perform(get("/v1/accounts/" + accountNumber2 + "/transactions/" + transactionId)
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    // Scenario: User tries to create a transaction without authentication
    // Given a user is not authenticated
    // When the user makes a POST request to the /v1/accounts/{accountId}/transactions endpoint
    // Then the system returns a Forbidden status code
    @Test
    void createTransaction_shouldReturn401IfNoAuth() throws Exception {
        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(100.0);
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.DEPOSIT);

        mockMvc.perform(post("/v1/accounts/01234567/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createTransaction_shouldReturn400ForInvalidRequest() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);

        CreateTransactionRequest req = new CreateTransactionRequest();
        // Missing required fields

        mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}