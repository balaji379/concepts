package com.concept.gateway.config;

import com.concept.gateway.clients.AuthClient;
import com.concept.gateway.modal.AuthenticationRequest;
import com.concept.gateway.service.RouterValidator;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    RouterValidator routerValidator;

    @Autowired
    AuthClient authClient;


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routerValidator.validate.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("authentication header is missing");
                }
                String authheader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authheader != null && authheader.startsWith("Bearer")) {
                    String token = authheader.substring(7);
                    AuthenticationRequest authRequest = new AuthenticationRequest();
                    authRequest.setToken(token);
                    try {
                        if (authClient.validateToken(authRequest)) {

                        }
                    } catch (Exception e) {
                        System.err.println(e);
                        throw e;
                    }
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
