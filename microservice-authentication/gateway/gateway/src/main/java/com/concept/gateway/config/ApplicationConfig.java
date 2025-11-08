package com.concept.gateway.config;

import com.concept.gateway.clients.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ApplicationConfig {

    @Autowired
    LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("lb://jwt-authentication-for-myservice")
                .filter(loadBalancedExchangeFilterFunction)
                .build();
    }

    @Bean
    public AuthClient authClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient()))
                .build();
        return httpServiceProxyFactory.createClient(AuthClient.class);
    }
}
