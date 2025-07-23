package com.eaglebank.api.controller.user;

import static java.net.HttpURLConnection.HTTP_CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eaglebank.api.controller.ControllerConstants;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.service.UserService;

/**
 * REST controller to handle user-related operations.
 */
@RestController
@RequestMapping(ControllerConstants.USERS_PATH)
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> post(@RequestBody CreateUserRequest newUser) {
        return ResponseEntity.status(HTTP_CREATED).body(userService.createUser(newUser));
    }
}
