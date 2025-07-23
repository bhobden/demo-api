package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.entity.bankaccount.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest extends BaseITest {

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

    @Test
    void listAccounts_shouldReturn200() throws Exception {

        String userName = createUser();
        String jwtToken = loginUser(userName);
        String accountNumber = createAccount(jwtToken);
        String accountNumber2 = createAccount(jwtToken);

        mockMvc.perform(get("/v1/accounts")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts[0].accountNumber")
                        .value(accountNumber))
                .andExpect(jsonPath("$.accounts[1].accountNumber")
                        .value(accountNumber2));
    }

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
                .andExpect(status().isBadRequest());
    }
}