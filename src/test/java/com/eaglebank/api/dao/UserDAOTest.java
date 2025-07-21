package com.eaglebank.api.dao;

import com.eaglebank.api.model.user.UserEntity;
import com.eaglebank.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDAO userDAO;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId("usr-abc123");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("+441234567890");
        user.setPassword("password");
    }

    @Test
    void getUserById_returnsUser_whenExists() {
        when(userRepository.findById("usr-abc123")).thenReturn(java.util.Optional.of(user));
        UserEntity result = userDAO.getUser("usr-abc123");
        assertNotNull(result);
        assertEquals("usr-abc123", result.getId());
    }

    @Test
    void getUserById_returnsNull_whenNotExists() {
        when(userRepository.findById("usr-xyz999")).thenReturn(java.util.Optional.empty());
        UserEntity result = userDAO.getUser("usr-xyz999");
        assertNull(result);
    }

    @Test
    void createUser_savesUser() {
        userDAO.createUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_savesUser() {
        userDAO.updateUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserById_deletesUser() {
        userDAO.deleteUser("usr-abc123");
        verify(userRepository, times(1)).deleteById("usr-abc123");
    }

    @Test
    void createUser_nullUser_doesNotThrow() {
        assertDoesNotThrow(() -> userDAO.createUser(null));
        verify(userRepository, times(1)).save(null);
    }

    @Test
    void updateUser_nullUser_doesNotThrow() {
        assertDoesNotThrow(() -> userDAO.updateUser(null));
        verify(userRepository, times(1)).save(null);
    }

    @Test
    void deleteUserById_nullId_doesNotThrow() {
        assertDoesNotThrow(() -> userDAO.deleteUser(null));
        verify(userRepository, times(1)).deleteById(null);
    }
}