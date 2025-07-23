package com.eaglebank.api.validation;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.eaglebank.api.dao.AccountDAO;
import com.eaglebank.api.dao.UserDAO;
import com.eaglebank.api.model.dto.request.CreateUserRequest;
import com.eaglebank.api.model.entity.user.UserEntity;
import com.eaglebank.api.model.entity.user.address.Address;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

/**
 * Provides validation logic for user-related operations such as authentication,
 * authorization, and user data integrity checks.
 * <p>
 * This class is used to ensure that user input and access patterns conform to
 * business rules and security requirements before any persistence or sensitive
 * operations are performed.
 * </p>
 */
@Component
public class UserValidation extends AbstractValidation {

    /**
     * Data access object for user entities.
     */
    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected AccountDAO accountDAO;

    /**
     * Regex pattern for validating international phone numbers (E.164 format).
     * Example: +441234567890
     */
    private final static Pattern PHONE_REGEX = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    /**
     * Validates that the user ID is not blank.
     *
     * @param userId The user ID to validate.
     * @throws ValidationException if the user ID is blank or null.
     */
    public void validateUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            invalid(ValidationExceptionType.AUTH_INVALID_USERID);
        }
    }

    /**
     * Validates that the password is not blank.
     *
     * @param password The password to validate.
     * @throws ValidationException if the password is blank or null.
     */
    public void validatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            invalid(ValidationExceptionType.AUTH_INVALID_PASSWORD);
        }
    }

    /**
     * Validates that the user exists in the repository.
     *
     * @param userId The user ID to validate.
     * @throws ValidationException if the user does not exist.
     */
    public void validateUserExists(String userId) {
        UserEntity user = userDAO.getUser(userId);
        validateUserExists(user);
    }

    /**
     * Validates that the user entity exists (is not null).
     *
     * @param user The UserEntity to validate.
     * @throws ValidationException if the user is null.
     */
    public void validateUserExists(UserEntity user) {
        if (user == null) {
            invalid(ValidationExceptionType.AUTH_INVALID_USER);
        }
        // Additional checks can be added here, such as checking if the user is active
        // or has the necessary permissions.
    }

    /**
     * Validates that the requester can access the specified user.
     * Checks authentication, authorization, and existence.
     *
     * @param userId The user ID being accessed.
     * @throws ValidationException if any check fails.
     */
    public void validateCanAccessUser(String userId) {
        validateUserAuthenticated();
        validateRequesterCanAccessUser(userId);
        validateUserExists(userId);
    }

    /**
     * Validates that the requester is authorized to access the specified user.
     * The requester must be authenticated and their username must match the userId.
     *
     * @param userId The user ID being accessed.
     * @throws ValidationException if unauthorized.
     */
    public void validateRequesterCanAccessUser(String userId) {
        String requesterId = AuthUtils.getUsername();
        validateUserId(requesterId);
        validateUserId(userId);
        if (!StringUtils.equals(requesterId, userId)) {
            invalid(ValidationExceptionType.AUTH_UNAUTHORIZED);
        }
    }

    /**
     * Validates that the current user is authenticated.
     *
     * @throws ValidationException if the user is not authenticated.
     */
    public void validateUserAuthenticated() {
        if (!AuthUtils.isAuthenticated()) {
            invalid(ValidationExceptionType.AUTH_UNAUTHORIZED);
        }
    }

    /**
     * Validates that both username and password are provided and not blank.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @throws ValidationException if either is blank or null.
     */
    public void validateUserCredentials(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            invalid(ValidationExceptionType.AUTH_INVALID_CREDENTIALS);
        }
        // Additional validation logic can be added here
    }

    /**
     * Validates the basic fields of an address.
     *
     * @param address The Address object to validate.
     * @throws ValidationException if any required field is blank or null.
     */
    public void validateAddress(Address address) {
        // Ideally the validation would be broken down much further, but for now we will
        // just check the basics
        if (address == null || StringUtils.isBlank(address.getLine1())
                || StringUtils.isBlank(address.getTown()) || StringUtils.isBlank(address.getCounty())
                || StringUtils.isBlank(address.getPostcode())) {
            invalid(ValidationExceptionType.USER_INVALID_ADDRESS);
        }
    }

    /**
     * Validates that the phone number is not blank and matches the expected E.164 format.
     *
     * @param phoneNumber The phone number to validate.
     * @throws ValidationException if the phone number is blank or invalid.
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber) || !PHONE_REGEX.matcher(phoneNumber).matches()) {
            invalid(ValidationExceptionType.USER_INVALID_PHONE_NUMBER);
        }
    }

    /**
     * Validates that the email is not blank.
     *
     * @param email The email to validate.
     * @throws ValidationException if the email is blank or null.
     */
    public void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            invalid(ValidationExceptionType.USER_INVALID_EMAIL);
        }
    }

    /**
     * Validates that the name is not blank.
     *
     * @param name The name to validate.
     * @throws ValidationException if the name is blank or null.
     */
    public void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            invalid(ValidationExceptionType.USER_INVALID_NAME);
        }
    }

    /**
     * Validates all fields required for creating a new user.
     *
     * @param newUser The CreateUserRequest containing user details.
     * @throws ValidationException if any field is invalid.
     */
    public void validateNewUser(CreateUserRequest newUser) {
        validateName(newUser.getName());
        validateAddress(newUser.getAddress());
        validatePhoneNumber(newUser.getPhoneNumber());
        validateEmail(newUser.getEmail());
        validatePassword(newUser.getPassword());
    }

    /**
     * Validates that the user has no associated accounts.
     *
     * @param userId The user ID to check.
     * @throws ValidationException if the user has one or more accounts.
     */
    public void validateUserHasNoAccounts(String userId) {
        if(!CollectionUtils.isEmpty(accountDAO.getUsersAccount(userId))){
            invalid(ValidationExceptionType.AUTH_USER_HAS_ACCOUNTS);
        }
    }
}
