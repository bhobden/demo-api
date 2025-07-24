package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.entity.bankaccount.Currency;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransactionControllerTest extends BaseITest {

    @Test
    void createTransaction_shouldReturn201() throws Exception {
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
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

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

    @Test
    void fetchTransaction_shouldReturn404IfNotFound() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);
        mockMvc.perform(get("/v1/accounts/" + accountNumber + "/transactions/tan-notfound")
                .header("Authorization", jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTransaction_shouldReturn403IfForbidden() throws Exception {
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
    void createTransaction_shouldReturn422IfInsufficientFunds() throws Exception {
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
                .andExpect(status().isUnprocessableEntity());
    }
}