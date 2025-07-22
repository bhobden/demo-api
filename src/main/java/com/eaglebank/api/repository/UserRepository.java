package com.eaglebank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.user.UserEntity;

/**
 * JPA repository interface to access User entities.
 * Spring Data JPA automatically implements the method based on signature.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    
}
