package com.service.employee.config;

import com.service.employee.client.AddressClient;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;


    public WebClient addressWebClient() {
        return WebClient.builder()
                .baseUrl("lb://address-service")
                .filter(loadBalancedExchangeFilterFunction)
                .build();
    }

    @Bean
    public AddressClient addressClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(addressWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(AddressClient.class);
    }

    @Bean
    TomcatProtocolHandlerCustomizer<ProtocolHandler> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
