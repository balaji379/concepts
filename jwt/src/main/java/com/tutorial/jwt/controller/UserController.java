package com.tutorial.jwt.controller;

import com.tutorial.jwt.model.AuthenticationRequest;
import com.tutorial.jwt.model.AuthenticationResponse;
import com.tutorial.jwt.model.RegisterRequest;
import com.tutorial.jwt.service.AuthenticationService;
import com.tutorial.jwt.service.JwtFilter;
import com.tutorial.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println(registerRequest);
        return ResponseEntity.ok().body(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok().body(authenticationService.authenticate(authenticationRequest));
    }
}
