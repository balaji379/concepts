package com.concept.jwt_authentication_for_myservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JwtAuthenticationForMyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationForMyserviceApplication.class, args);
	}

}
