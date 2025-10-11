package com.vb.grpc_jwt.config;

import com.vb.grpc_jwt.EmployeeServiceGrpc;
import com.vb.grpc_jwt.Jwt.JwtProvider;
import org.lognet.springboot.grpc.security.GrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurer;
import org.lognet.springboot.grpc.security.GrpcSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Service;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public AuthenticationManager buildauthprovider() {
        return new ProviderManager(jwtProvider);
    }

    

}
