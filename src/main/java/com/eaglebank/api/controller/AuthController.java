package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.LoginRequest;
import com.eaglebank.api.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to handle login requests and issue JWTs.
 */
@RestController
@RequestMapping(ControllerConstants.LOGIN_PATH)
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * POST
     * Validates credentials and returns a JWT token if successful.
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(userService.authoriseUser(request));
    }

}
