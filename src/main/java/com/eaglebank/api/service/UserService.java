package com.eaglebank.api.service;

import com.eaglebank.api.metrics.MetricScope;
import com.eaglebank.api.metrics.MetricScopeFactory;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.dto.request.LoginRequest;
import com.eaglebank.api.model.dto.request.UpdateUserRequest;
import com.eaglebank.api.model.dto.response.UserResponse;
import com.eaglebank.api.model.user.UserEntity;
import com.eaglebank.api.repository.UserRepository;
import com.eaglebank.api.security.IdGenerator;
import com.eaglebank.api.security.JwtUtil;
import com.eaglebank.api.validation.UserValidation;
import com.eaglebank.api.validation.exception.ValidationException;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

import io.jsonwebtoken.lang.Objects;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user-related business logic, including
 * authentication,
 * user creation, and user retrieval. Handles validation, password encoding, and
 * JWT generation.
 */
@Service
public class UserService extends AbstractService {

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * @param request LoginRequest containing username and password.
     * @return ResponseEntity with JWT token if authentication is successful.
     * @throws ValidationException if credentials are invalid.
     */
    public Object getJWTToken(LoginRequest request) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.generate.duration")) {

            UserEntity user = userRepository.findById(request.getUsername());
            if (user == null) {
                throw new ValidationException(ValidationExceptionType.AUTH_INVALID_CREDENTIALS);
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new ValidationException(ValidationExceptionType.AUTH_INVALID_CREDENTIALS);
            }

            String token = jwtUtil.generateToken(request.getUsername());

            // Also return token in JSON body
            return ResponseEntity.ok(Map.of("jwt", token));

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
     * @throws ValidationException if the user ID already exists.
     */
    public UserResponse createUser(CreateUserRequest newUser) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.create.duration")) {
            // Check if user already exists
            String newId = IdGenerator.generateUserId();
            if (userRepository.findById(newId) != null) {
                throw new ValidationException(ValidationExceptionType.ID_ALREADY_EXISTS);
            }

            // Create new user and copy all fields from CreateUserRequest
            UserEntity user = new UserEntity();
            user.setId(newId);
            user.setName(newUser.getName());
            user.setAddress(newUser.getAddress());
            user.setPhoneNumber(newUser.getPhoneNumber());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            // Set timestamps
            user.setCreatedTimestamp(java.time.LocalDateTime.now());
            user.setUpdatedTimestamp(java.time.LocalDateTime.now());

            // Save to repository
            userRepository.save(user);

            return new UserResponse(user);
        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    /**
     * Retrieves a user by ID, ensuring the authenticated user matches the requested
     * user ID.
     *
     * @param authentication The authentication object representing the current
     *                       user.
     * @param userId         The ID of the user to retrieve.
     * @return UserResponse containing the user's details.
     * @throws ValidationException if the user is unauthorized or not found.
     */
    public UserResponse getUserById(String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.get.duration")) {

            userValidation.validateUserAuthenticated();
            userValidation.validateRequesterCanAccessUser(userId);

            UserEntity user = userRepository.findById(userId);
            userValidation.validateUserExists(user);

            return new UserResponse(user);

        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.update.duration")) {

            userValidation.validateUserAuthenticated();
            userValidation.validateRequesterCanAccessUser(userId);

            UserEntity user = userRepository.findById(userId);
            userValidation.validateUserExists(user);

            if (updateUserRequest.getName() != null && !updateUserRequest.getName().equals(user.getName())) {
                userValidation.validateName(updateUserRequest.getName());
                user.setName(updateUserRequest.getName());
            }
            if (updateUserRequest.getAddress() != null
                    && !updateUserRequest.getAddress().equals(user.getAddress())) {
                userValidation.validateAddress(updateUserRequest.getAddress());
                user.setAddress(updateUserRequest.getAddress());
            }
            if (updateUserRequest.getPhoneNumber() != null
                    && !updateUserRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
                userValidation.validatePhoneNumber(updateUserRequest.getPhoneNumber());
                user.setPhoneNumber(updateUserRequest.getPhoneNumber());
            }
            if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().equals(user.getEmail())) {
                userValidation.validateEmail(updateUserRequest.getEmail());
                user.setEmail(updateUserRequest.getEmail());
            }

            userRepository.updateUserById(userId, user);

            return new UserResponse(user);

        } catch (Exception e) {
            handleException(e);
            return null; // Unreachable, but required for compilation
        }
    }

    public void deleteUser(String userId) {
        try (MetricScope scope = MetricScopeFactory.of("eaglebank.user.delete.duration")) {

            userValidation.validateUserAuthenticated();
            userValidation.validateRequesterCanAccessUser(userId);

            UserEntity user = userRepository.findById(userId);
            userValidation.validateUserExists(user);

            userRepository.deleteById(userId);

        } catch (Exception e) {
            handleException(e);
        }
    }
}
