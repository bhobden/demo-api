package com.eaglebank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.user.UserEntity;

/**
 * Repository interface for accessing and managing {@link UserEntity} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations for users.
 * Spring Data JPA automatically implements the methods based on their signatures.
 * </p>
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    
}
