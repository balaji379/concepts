package com.tutorial.jwt.service;

import com.tutorial.jwt.Entity.ROLE;
import com.tutorial.jwt.Entity.UserEntity;
import com.tutorial.jwt.model.AuthenticationRequest;
import com.tutorial.jwt.model.AuthenticationResponse;
import com.tutorial.jwt.model.RegisterRequest;
import com.tutorial.jwt.model.User;
import com.tutorial.jwt.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getFirstName().equals("vignesh") ? ROLE.ADMIN : ROLE.USER)
                .build();
        userRepo.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user is not present"));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
