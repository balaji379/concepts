package com.concept.jwt_authentication_for_myservice.repo;

import com.concept.jwt_authentication_for_myservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    public Optional<UserEntity> findByEmail(String email);
}
