package com.eaglebank.api.validation;

import com.eaglebank.api.model.address.Address;
import com.eaglebank.api.model.user.UserEntity;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.validation.exception.ValidationException;
import com.eaglebank.api.validation.exception.ValidationExceptionType;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidationTest {

    private UserValidation userValidation;

    @BeforeEach
    void setUp() {
        userValidation = new UserValidation();
    }

    @Test
    void validateUserId_nullOrBlank_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserId(null));
        assertEquals(ValidationExceptionType.AUTH_INVALID_USERID, ex.getType());
        assertTrue(StringUtils.isNotBlank(ex.getMessage()));

        ex = assertThrows(ValidationException.class, () -> userValidation.validateUserId(""));
        assertEquals(ValidationExceptionType.AUTH_INVALID_USERID, ex.getType());
    }

    @Test
    void validatePassword_nullOrBlank_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validatePassword(null));
        assertEquals(ValidationExceptionType.AUTH_INVALID_PASSWORD, ex.getType());
        assertTrue(StringUtils.isNotBlank(ex.getMessage()));

        ex = assertThrows(ValidationException.class, () -> userValidation.validatePassword(""));
        assertEquals(ValidationExceptionType.AUTH_INVALID_PASSWORD, ex.getType());
    }

    @Test
    void validateUserExists_null_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserExists((UserEntity) null));
        assertEquals(ValidationExceptionType.AUTH_INVALID_USER, ex.getType());
        assertTrue(StringUtils.isNotBlank(ex.getMessage()));
    }

    @Test
    void validateRequesterCanAccessUser_differentIds_throwsExceptionWithTypeAndMessage() {
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            authUtilsMock.when(AuthUtils::getUsername).thenReturn("usr-abc123");
            ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateRequesterCanAccessUser("usr-def456"));
            assertEquals(ValidationExceptionType.AUTH_UNAUTHORIZED, ex.getType());
            assertTrue(StringUtils.isNotBlank(ex.getMessage()));
        }
    }

    @Test
    void validateRequesterCanAccessUser_nullIds_throwsExceptionWithType() {
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            authUtilsMock.when(AuthUtils::getUsername).thenReturn(null);
            ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateRequesterCanAccessUser(null));
            assertEquals(ValidationExceptionType.AUTH_INVALID_USERID, ex.getType());
        }
    }

    @Test
    void validateUserAuthenticated_authenticated_throwsExceptionWithTypeAndMessage() {
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            authUtilsMock.when(AuthUtils::isAuthenticated).thenReturn(true);
            ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserAuthenticated());
            assertEquals(ValidationExceptionType.AUTH_UNAUTHORIZED, ex.getType());
            assertTrue(StringUtils.isNotBlank(ex.getMessage()));
        }
    }

    @Test
    void validateUserCredentials_nullOrBlank_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials(null, "password"));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials("username", null));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials("", "password"));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials("username", ""));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());
    }

    @Test
    void validateAddress_nullOrMissingFields_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(null));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());

        Address address = new Address();
        ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(address));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());

        address.setLine1("Line1");
        ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(address));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());

        address.setTown("Town");
        ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(address));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());

        address.setCounty("County");
        ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(address));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());

        address.setPostcode(null);
        ex = assertThrows(ValidationException.class, () -> userValidation.validateAddress(address));
        assertEquals(ValidationExceptionType.USER_INVALID_ADDRESS, ex.getType());
    }

    @Test
    void validatePhoneNumber_nullOrBlankOrInvalid_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validatePhoneNumber(null));
        assertEquals(ValidationExceptionType.USER_INVALID_PHONE_NUMBER, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validatePhoneNumber(""));
        assertEquals(ValidationExceptionType.USER_INVALID_PHONE_NUMBER, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validatePhoneNumber("12345"));
        assertEquals(ValidationExceptionType.USER_INVALID_PHONE_NUMBER, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validatePhoneNumber("+"));
        assertEquals(ValidationExceptionType.USER_INVALID_PHONE_NUMBER, ex.getType());
    }

    @Test
    void validateEmail_nullOrBlank_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateEmail(null));
        assertEquals(ValidationExceptionType.USER_INVALID_EMAIL, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateEmail(""));
        assertEquals(ValidationExceptionType.USER_INVALID_EMAIL, ex.getType());
    }

    @Test
    void validateName_nullOrBlank_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateName(null));
        assertEquals(ValidationExceptionType.USER_INVALID_NAME, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateName(""));
        assertEquals(ValidationExceptionType.USER_INVALID_NAME, ex.getType());
    }

    @Test
    void validateUserId_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validateUserId("usr-abc123"));
    }

    @Test
    void validatePassword_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validatePassword("password123"));
    }

    @Test
    void validateUserExists_notNull_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validateUserExists(new UserEntity()));
    }

    @Test
    void validateRequesterCanAccessUser_sameIds_doesNotThrow() {
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            authUtilsMock.when(AuthUtils::getUsername).thenReturn("usr-abc123");
            assertDoesNotThrow(() -> userValidation.validateRequesterCanAccessUser("usr-abc123"));
        }
    }

    @Test
    void validateUserAuthenticated_notAuthenticated_doesNotThrow() {
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            authUtilsMock.when(AuthUtils::isAuthenticated).thenReturn(false);
            assertDoesNotThrow(() -> userValidation.validateUserAuthenticated());
        }
    }

    @Test
    void validateUserCredentials_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validateUserCredentials("username", "password"));
    }

    @Test
    void validateAddress_valid_doesNotThrow() {
        Address address = new Address();
        address.setLine1("Line1");
        address.setTown("Town");
        address.setCounty("County");
        address.setPostcode("Postcode");
        assertDoesNotThrow(() -> userValidation.validateAddress(address));
    }

    @Test
    void validatePhoneNumber_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validatePhoneNumber("+441234567890"));
    }

    @Test
    void validateEmail_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validateEmail("test@example.com"));
    }

    @Test
    void validateName_valid_doesNotThrow() {
        assertDoesNotThrow(() -> userValidation.validateName("Test User"));
    }

    @Test
    void validateUserId_whitespace_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserId("   "));
        assertEquals(ValidationExceptionType.AUTH_INVALID_USERID, ex.getType());
        assertTrue(StringUtils.isNotBlank(ex.getMessage()));
    }

    @Test
    void validatePassword_whitespace_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validatePassword("   "));
        assertEquals(ValidationExceptionType.AUTH_INVALID_PASSWORD, ex.getType());
        assertTrue(StringUtils.isNotBlank(ex.getMessage()));
    }

    @Test
    void validateUserCredentials_whitespace_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials("   ", "password"));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());

        ex = assertThrows(ValidationException.class, () -> userValidation.validateUserCredentials("username", "   "));
        assertEquals(ValidationExceptionType.AUTH_INVALID_CREDENTIALS, ex.getType());
    }

    @Test
    void validateEmail_whitespace_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateEmail("   "));
        assertEquals(ValidationExceptionType.USER_INVALID_EMAIL, ex.getType());
    }

    @Test
    void validateName_whitespace_throwsExceptionWithTypeAndMessage() {
        ValidationException ex = assertThrows(ValidationException.class, () -> userValidation.validateName("   "));
        assertEquals(ValidationExceptionType.USER_INVALID_NAME, ex.getType());
    }

}