package com.eaglebank.api.service;

import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.request.LoginRequest;
import com.eaglebank.api.model.dto.request.UpdateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.model.entity.user.UserEntity;
import com.eaglebank.api.security.IdGenerator;
import com.eaglebank.api.validation.exception.ValidationException;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user-related business logic, including authentication,
 * user creation, retrieval, update, and deletion. Handles validation, password encoding,
 * and JWT generation.
 */
@Service
public class UserService extends AbstractService {

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * @param request LoginRequest containing username and password.
     * @return A map containing the JWT token if authentication is successful.
     * @throws ValidationException if credentials are invalid.
     */
    public Object authoriseUser(LoginRequest request) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.generate.duration")) {
            UserEntity user = userDAO.getUser(request.getUsername());
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new ValidationException(ValidationExceptionType.AUTH_INVALID_CREDENTIALS);
            }
            String token = jwtUtil.generateToken(request.getUsername());
            return Map.of("jwt", token);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Creates a new user in the system.
     *
     * @param newUser The user creation request containing all user details.
     * @return UserResponse containing the created user's details.
     * @throws ValidationException if the user ID already exists or validation fails.
     */
    public UserResponse createUser(CreateUserRequest newUser) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.create.duration")) {
            String newId = IdGenerator.generateUserId();
            if (userDAO.getUser(newId) != null) {
                throw new ValidationException(ValidationExceptionType.ID_ALREADY_EXISTS);
            }
            userValidation.validateNewUser(newUser);

            UserEntity user = new UserEntity();
            user.setId(newId);
            user.setName(newUser.getName());
            user.setAddress(newUser.getAddress());
            user.setPhoneNumber(newUser.getPhoneNumber());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setCreatedTimestamp(java.time.LocalDateTime.now());
            user.setUpdatedTimestamp(java.time.LocalDateTime.now());

            userDAO.createUser(user);
            return userResponse(user);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Retrieves a user by ID, ensuring the authenticated user matches the requested user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return UserResponse containing the user's details.
     * @throws ValidationException if the user is unauthorized or not found.
     */
    public UserResponse getUser(String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.get.duration")) {
            return userResponse(fetchAndValidateUser(userId));
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Updates an existing user's details.
     *
     * @param userId            The ID of the user to update.
     * @param updateUserRequest The request containing updated user fields.
     * @return UserResponse containing the updated user's details.
     * @throws ValidationException if validation fails or user is not found.
     */
    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.update.duration")) {
            UserEntity user = fetchAndValidateUser(userId);

            if (updateUserRequest.getName() != null && !updateUserRequest.getName().equals(user.getName())) {
                userValidation.validateName(updateUserRequest.getName());
                user.setName(updateUserRequest.getName());
            }
            if (updateUserRequest.getAddress() != null && !updateUserRequest.getAddress().equals(user.getAddress())) {
                userValidation.validateAddress(updateUserRequest.getAddress());
                user.setAddress(updateUserRequest.getAddress());
            }
            if (updateUserRequest.getPhoneNumber() != null && !updateUserRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
                userValidation.validatePhoneNumber(updateUserRequest.getPhoneNumber());
                user.setPhoneNumber(updateUserRequest.getPhoneNumber());
            }
            if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().equals(user.getEmail())) {
                userValidation.validateEmail(updateUserRequest.getEmail());
                user.setEmail(updateUserRequest.getEmail());
            }
            if (StringUtils.isNotBlank(updateUserRequest.getPassword())
                    && !passwordEncoder.matches(updateUserRequest.getPassword(), user.getPassword())) {
                userValidation.validatePassword(updateUserRequest.getPassword());
                user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            }

            userDAO.updateUser(user);
            return userResponse(user);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     * @throws ValidationException if the user is unauthorized, not found, or has accounts.
     */
    public void deleteUser(String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.delete.duration")) {
            fetchAndValidateUser(userId);
            userValidation.validateUserHasNoAccounts(userId);
            userDAO.deleteUser(userId);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Validates and fetches a user by ID, ensuring authentication and access rights.
     *
     * @param userId The ID of the user to validate and fetch.
     * @return The UserEntity if validation passes.
     * @throws ValidationException if validation fails.
     */
    protected UserEntity fetchAndValidateUser(String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.validate.duration")) {
            userValidation.validateUserAuthenticated();
            UserEntity user = userDAO.getUser(userId);
            userValidation.validateUserExists(user);
            userValidation.validateRequesterCanAccessUser(userId);
            return user;
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Maps a UserEntity to a UserResponse DTO.
     *
     * @param user The UserEntity to map.
     * @return The UserResponse DTO.
     */
    protected UserResponse userResponse(UserEntity user) {
        return new UserResponse()
                .setId(user.getId())
                .setName(user.getName())
                .setAddress(user.getAddress())
                .setPhoneNumber(user.getPhoneNumber())
                .setEmail(user.getEmail())
                .setCreatedTimestamp(user.getCreatedTimestamp())
                .setUpdatedTimestamp(user.getUpdatedTimestamp());
    }
}
