package com.concept.gateway.clients;

import com.concept.gateway.modal.AuthenticationRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/auth")
public interface AuthClient {

    @PostExchange("/validate-token")
    public boolean validateToken(@RequestBody AuthenticationRequest request);

}
