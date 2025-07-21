package com.eaglebank.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eaglebank.api.dao.AccountDAO;
import com.eaglebank.api.dao.UserDAO;
import com.eaglebank.api.security.JwtUtil;
import com.eaglebank.api.validation.AccountValidation;
import com.eaglebank.api.validation.UserValidation;
import com.eaglebank.api.validation.exception.CommonException;
import com.eaglebank.api.validation.exception.CommonExceptionType;
import com.eaglebank.api.validation.exception.HttpRuntimeException;
import com.eaglebank.api.validation.exception.ValidationException;

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

    /**
     * Handles exceptions thrown in service methods and rethrows them as appropriate
     * custom exceptions.
     *
     * @param e The exception to handle.
     * @throws ValidationException or CommonException depending on the exception
     *                             type.
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
}
