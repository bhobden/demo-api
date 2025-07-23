package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.request.UpdateUserRequest;
import com.eaglebank.api.model.entity.user.address.Address;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends BaseITest {

    @Test
    void createUser_shouldReturn201() throws Exception {
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

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void fetchUser_shouldReturn200() throws Exception {

        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(get("/v1/users/"+userName)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userName));
    }

    @Test
    void updateUser_shouldReturn200() throws Exception {
        String userName = createUser();
        String jwtToken = loginUser(userName);
        
        UpdateUserRequest req = new UpdateUserRequest();
        req.setName("Updated User");
        // ...set other fields...

        mockMvc.perform(patch("/v1/users/"+userName)
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void deleteUser_shouldReturn204() throws Exception {

        String userName = createUser();
        String jwtToken = loginUser(userName);

        mockMvc.perform(delete("/v1/users/"+userName)
                .header("Authorization", jwtToken))
                .andExpect(status().isNoContent());
    }
}