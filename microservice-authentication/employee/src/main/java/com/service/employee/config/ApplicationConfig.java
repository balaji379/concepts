package com.service.employee.config;

import com.service.employee.client.AddressClient;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

import org.apache.coyote.ProtocolHandler;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
=======
>>>>>>> parent of 83ba36b (microservice-authentication)
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;


    public WebClient addressWebClient() {
        return WebClient.builder()
                .baseUrl("http://address-service")
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
<<<<<<< HEAD


//    @Bean
//    TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreads() {
//        return protocolHandler -> {
//            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
//        };
//    }

    @Bean
    TomcatProtocolHandlerCustomizer<ProtocolHandler> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
=======
>>>>>>> parent of 83ba36b (microservice-authentication)
}
