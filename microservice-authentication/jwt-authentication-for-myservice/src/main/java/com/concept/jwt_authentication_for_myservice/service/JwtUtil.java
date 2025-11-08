package com.concept.jwt_authentication_for_myservice.service;

import com.concept.jwt_authentication_for_myservice.entity.UserEntity;
import com.concept.jwt_authentication_for_myservice.repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    final String secreteKey = "6WztWZJz8fQeF6zkJ2t6DkUn8/1C3wN5ohb3Oq2Dk1A=";
   // private final UserRepo userRepo;


    public boolean isTokenValid(String token) {
        try {
            if (isTokenExpriation(token))
                return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpriation(String token) {
        Date date = extractExpiration(token);
        return date.before(new Date());
    }

    public String generateToken(UserDetails userDetails,String email) {

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .signWith(getSignKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsresolver) {
        Claims claims = extractAllClaims(token);
        return claimsresolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(key);
    }


}
