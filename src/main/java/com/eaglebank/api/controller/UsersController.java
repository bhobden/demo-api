package com.eaglebank.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * REST controller to handle user-related operations.
 */
@RestController
@RequestMapping(ControllerConstants.USERS_PATH)
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody CreateUserRequest newUser, HttpServletResponse response) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
