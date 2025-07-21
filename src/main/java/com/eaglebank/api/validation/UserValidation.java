package com.eaglebank.api.validation;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.eaglebank.api.model.address.Address;
import com.eaglebank.api.model.user.UserEntity;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

/**
 * Provides validation logic for user-related operations such as authentication,
 * authorization, and user data integrity checks.
 */
@Component
public class UserValidation extends AbstractValidation {

    // Regex pattern for validating international phone numbers (E.164 format)
    private final static Pattern PHONE_REGEX = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    /**
     * Validates that the user ID is not blank.
     *
     * @param userId The user ID to validate.
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
     */
    public void validatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            invalid(ValidationExceptionType.AUTH_INVALID_PASSWORD);
        }
    }

    /**
     * Validates that the user entity exists (is not null).
     *
     * @param user The UserEntity to validate.
     */
    public void validateUserExists(UserEntity user) {
        if (user == null) {
            invalid(ValidationExceptionType.AUTH_INVALID_USER);
        }
    }

    /**
     * Validates that the requester is authorized to access the specified user.
     *
     * @param userId The user ID being accessed.
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
     */
    public void validateUserAuthenticated() {
        if (AuthUtils.isAuthenticated()) {
            invalid(ValidationExceptionType.AUTH_UNAUTHORIZED);
        }
    }

    /**
     * Validates that both username and password are provided and not blank.
     *
     * @param username The username to validate.
     * @param password The password to validate.
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
     */
    public void validateAddress(Address address) {
        // Ideally the validation would be broken down much further, but for now we will just check the basics
        if (address == null || StringUtils.isBlank(address.getLine1())
                || StringUtils.isBlank(address.getTown()) || StringUtils.isBlank(address.getCounty())
                || StringUtils.isBlank(address.getPostcode())) {
            invalid(ValidationExceptionType.USER_INVALID_ADDRESS);
        }
    }

    /**
     * Validates that the phone number is not blank and matches the expected format.
     *
     * @param phoneNumber The phone number to validate.
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
     */
    public void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            invalid(ValidationExceptionType.USER_INVALID_NAME);
        }
    }
}
