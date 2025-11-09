package com.concept.gateway.config;

import com.concept.gateway.clients.AuthClient;
import com.concept.gateway.modal.AuthenticationRequest;
import com.concept.gateway.service.RouterValidator;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private AuthClient authClient;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // Check if route requires authentication
            if (routerValidator.validate.test(exchange.getRequest())) {

                // Verify Authorization header
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    AuthenticationRequest authRequest = new AuthenticationRequest();
                    authRequest.setToken(token);

                    // Reactive call to auth service
                    return authClient.validateToken(authRequest)
                            .flatMap(isValid -> {
                                if (Boolean.TRUE.equals(isValid)) {
                                    // Proceed with request
                                    return chain.filter(exchange);
                                } else {
                                    // Token invalid
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }
                            })
                            .onErrorResume(e -> {
                                // Any error (network, etc.)
                                e.printStackTrace();
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            });
                } else {
                    // Invalid token format
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }

            // If route doesn’t need authentication
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Empty class - can be extended later if needed
    }
}
