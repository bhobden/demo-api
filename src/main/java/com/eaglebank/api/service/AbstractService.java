package com.eaglebank.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eaglebank.api.dao.AccountDAO;
import com.eaglebank.api.dao.TransactionDAO;
import com.eaglebank.api.dao.UserDAO;
import com.eaglebank.api.security.JwtUtil;
import com.eaglebank.api.validation.AccountValidation;
import com.eaglebank.api.validation.TransactionValidation;
import com.eaglebank.api.validation.UserValidation;
import com.eaglebank.api.validation.exception.CommonException;
import com.eaglebank.api.validation.exception.CommonExceptionType;
import com.eaglebank.api.validation.exception.HttpRuntimeException;
import com.eaglebank.api.validation.exception.ValidationException;

/**
 * Abstract base class for service-layer classes in the EagleBank application.
 * <p>
 * Provides common dependencies (such as DAOs, validators, JWT utilities, and
 * password encoder)
 * and a unified exception handling method for use by subclasses.
 * </p>
 *
 * <h3>Injected Dependencies:</h3>
 * <ul>
 * <li>{@link JwtUtil} - Utility for JWT operations</li>
 * <li>{@link UserDAO} - Data access for user entities</li>
 * <li>{@link UserValidation} - Validation logic for user operations</li>
 * <li>{@link PasswordEncoder} - Password hashing and verification</li>
 * <li>{@link AccountDAO} - Data access for account entities</li>
 * <li>{@link AccountValidation} - Validation logic for account operations</li>
 * </ul>
 *
 * <h3>Exception Handling:</h3>
 * <ul>
 * <li>{@link #handleException(Exception)} - Converts and rethrows exceptions as
 * appropriate custom types</li>
 * </ul>
 */
public abstract class AbstractService {

    @Autowired
    protected JwtUtil jwtUtil;

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserValidation userValidation;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected AccountDAO accountDAO;

    @Autowired
    protected AccountValidation accountValidation;

    @Autowired
    protected TransactionDAO transactionDAO;

    @Autowired
    protected TransactionValidation transactionValidation;

    /**
     * Handles exceptions thrown in service methods and rethrows them as appropriate
     * custom exceptions.
     *
     * @param e The exception to handle.
     * @throws ValidationException  if the exception is a validation error.
     * @throws CommonException      if the exception is a common application error.
     * @throws HttpRuntimeException if the exception is a generic HTTP error.
     * @throws CommonException      wrapping the original exception for unknown
     *                              errors.
     */
    protected void handleException(Exception e) {
        if (e instanceof ValidationException) {
            throw (ValidationException) e;
        } else if (e instanceof CommonException) {
            throw (CommonException) e;
        } else if (e instanceof HttpRuntimeException) {
            throw (HttpRuntimeException) e;
        } else {
            throw new CommonException(CommonExceptionType.UNKNOWN_ERROR, e);
        }
    }

    /**
     * Converts an Instant to LocalDateTime in UTC, handling nulls safely.
     *
     * @param instant The Instant to convert.
     * @return LocalDateTime in UTC, or null if instant is null.
     */
    protected LocalDateTime convertInstantToLocalDateTimeUTC(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }
}