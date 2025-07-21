package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.UpdateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.USERS_WITHID_PATH)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieve user details by ID.
     *
     * @param userId the user ID path variable
     * @return UserResponse if found
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable("userId") String userId,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Update user details by ID.
     *
     * @param userId            the user ID path variable
     * @param updateUserRequest the request body with updated fields
     * @return UserResponse with updated user details
     */
    @PatchMapping
    public ResponseEntity<UserResponse> updateUserById(
            @PathVariable("userId") String userId,
            @RequestBody UpdateUserRequest updateUserRequest,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserRequest));
    }

    /**
     * Delete user by ID.
     *
     * @param userId the user ID path variable
     * @return ResponseEntity with no content
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(
            @PathVariable("userId") String userId,
            HttpServletRequest request) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
