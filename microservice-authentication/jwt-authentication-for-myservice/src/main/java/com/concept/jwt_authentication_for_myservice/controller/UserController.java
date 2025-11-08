package com.concept.jwt_authentication_for_myservice.controller;

import com.concept.jwt_authentication_for_myservice.entity.UserEntity;
import com.concept.jwt_authentication_for_myservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;

    @GetMapping("/get_user/{email}")
    public UserEntity userEntity(@PathVariable String email) {
        return userRepo.findByEmail(email).get();
    }
}
