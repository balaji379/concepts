package com.concept.jwt_authentication_for_myservice.modal;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
