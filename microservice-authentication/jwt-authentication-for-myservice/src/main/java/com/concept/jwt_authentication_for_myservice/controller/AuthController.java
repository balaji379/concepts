package com.concept.jwt_authentication_for_myservice.controller;

import com.concept.jwt_authentication_for_myservice.entity.UserEntity;
import com.concept.jwt_authentication_for_myservice.modal.AuthenticationRequest;
import com.concept.jwt_authentication_for_myservice.repo.UserRepo;
import com.concept.jwt_authentication_for_myservice.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/save-user")
    public String save_user(@RequestBody UserEntity entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepo.save(entity);
        return jwtUtil.generateToken(entity, entity.getEmail());
    }

    @GetMapping("/login/{username}")
    public String login(@PathVariable String username) {
        return jwtUtil.generateToken(userRepo.findByEmail(username).get(), username);
    }

    @PostMapping("/validate-token")
    public boolean validateToken(@RequestBody AuthenticationRequest request){
        String token = request.getToken();
        return jwtUtil.isTokenValid(token);
    }
}
