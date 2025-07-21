package com.eaglebank.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eaglebank.api.model.user.UserEntity;
import com.eaglebank.api.repository.UserRepository;

/**
 * Data Access Object (DAO) for user entities.
 * Provides methods to interact with the UserRepository for CRUD operations.
 */
@Repository
public class UserDAO {

    /**
     * User repository for accessing user data.
     */
    @Autowired
    protected UserRepository userRepository;

    /**
     * Retrieves a user entity by its ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserEntity if found, otherwise null.
     */
    public UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * Persists a new user entity in the repository.
     *
     * @param user The UserEntity to create.
     */
    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Deletes a user entity by its ID.
     *
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Updates an existing user entity in the repository.
     *
     * @param user The UserEntity to update.
     */
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }
}
