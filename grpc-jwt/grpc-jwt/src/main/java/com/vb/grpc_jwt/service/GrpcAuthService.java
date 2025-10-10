package com.vb.grpc_jwt.service;

import com.vb.grpc_jwt.AuthServiceGrpc;
import com.vb.grpc_jwt.JWTToken;
import com.vb.grpc_jwt.Jwt.JwtProvider;
import com.vb.grpc_jwt.JwtRequest;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@GRpcService
public class GrpcAuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private final String secretKey = "hello this is jwt secret key";
    @Autowired
    JwtProvider jwtProvider;
    private
    Instant now = Instant.now();
    Instant expiration = now.plus(1, ChronoUnit.HOURS);

    @Override
    public void authenticate(JwtRequest request, StreamObserver<JWTToken> responseObserver) {
        Authentication authentication = jwtProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        String authority = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        String username = request.getUserName();
        Jwts.builder()
                .claim("auth", authentication)
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
