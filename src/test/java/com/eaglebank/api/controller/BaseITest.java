package com.eaglebank.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.jayway.jsonpath.JsonPath;
import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.request.LoginRequest;
import com.eaglebank.api.model.entity.bankaccount.AccountType;
import com.eaglebank.api.model.entity.bankaccount.Currency;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionType;
import com.eaglebank.api.model.entity.user.address.Address;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseITest {

    protected final String NAME = "name test";
    protected final String PASSWORD = "password";
    protected final String EMAIL = "test@eaglebank.com";
    protected final String PHONE_NUMBER = "+353871234567";
    protected final String LINE1 = "123 Main St";
    protected final String TOWN = "Springfield";
    protected final String POSTCODE = "12345";
    protected final String COUNTY = "Essex";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    public String createUser() throws Exception {

        // Create a sample address
        Address address = new Address();
        address.setLine1(LINE1);
        address.setTown(TOWN);
        address.setPostcode(POSTCODE);
        address.setCounty(COUNTY);

        // Create a test user
        CreateUserRequest user = new CreateUserRequest();
        user.setName(NAME);
        user.setAddress(address);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        MvcResult createUserResult = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()).andReturn();

        return JsonPath.read(createUserResult.getResponse().getContentAsString(), "$.id");
    }

    public String loginUser(String username) throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(PASSWORD);

        MvcResult loginResult = mockMvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").isNotEmpty()).andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        return "Bearer " + JsonPath.read(responseBody, "$.jwt");
    }

    public String createAccount(String jwtToken) throws Exception {

        CreateBankAccountRequest req = new CreateBankAccountRequest();
        req.setAccountName("Personal Bank Account");
        req.setAccountType(AccountType.PERSONAL);

        MvcResult accountResult = mockMvc.perform(post("/v1/accounts")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").isNotEmpty())
                .andExpect(jsonPath("$.accountName").value("Personal Bank Account")).andReturn();

        return JsonPath.read(accountResult.getResponse().getContentAsString(), "$.accountNumber");
    }

    public String createTransaction(String accountNumber, String jwtToken) throws Exception {
        return createTransaction(accountNumber,jwtToken,100);
    }

    public String createTransaction(String accountNumber, String jwtToken, double amount) throws Exception {
        CreateTransactionRequest req = new CreateTransactionRequest();
        req.setAmount(amount);
        req.setCurrency(Currency.GBP);
        req.setType(TransactionType.DEPOSIT);

        MvcResult transactionResult = mockMvc.perform(post("/v1/accounts/" + accountNumber + "/transactions")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andReturn();

        return JsonPath.read(transactionResult.getResponse().getContentAsString(), "$.id");
    }
}
