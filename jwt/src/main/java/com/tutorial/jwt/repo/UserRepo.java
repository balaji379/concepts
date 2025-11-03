package com.tutorial.jwt.repo;

import com.tutorial.jwt.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    public Optional<UserEntity> findByEmail(String email);
}
