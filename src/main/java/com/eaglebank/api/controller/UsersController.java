package com.eaglebank.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Secure endpoint to retrieve the current authenticated user's identity.
 */
@RestController
@RequestMapping(ControllerConstants.USERS_ENDPOINT)
public class UsersController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users
     * Returns the username from the authenticated request.
     */
    @GetMapping
    public ResponseEntity<?> get(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return ResponseEntity.ok().body("Authenticated as: " + username);
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody CreateUserRequest newUser, HttpServletResponse response) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
