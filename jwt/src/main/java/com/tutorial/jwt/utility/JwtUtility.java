//package com.tutorial.jwt.utility;
//
//import com.tutorial.jwt.model.User;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtUtility {
//    List<User> list = new ArrayList<>();
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private final int expirationTime = 84600000;
//
//    @PostConstruct
//    public void generateUser() {
//        list.add(User.builder().username("balaji").password("1234").roles(Set.of("USER")).build());
//        list.add(User.builder().username("vignesh").password("1234").roles(Set.of("USER")).build());
//        list.add(User.builder().username("venkat").password("1234").roles(Set.of("USER")).build());
//        list.add(User.builder().username("vb").password("1234").roles(Set.of("USER")).build());
//        list.add(User.builder().username("veeshan").password("1234").roles(Set.of("USER")).build());
//    }
//
//    public String generateToken(String username) {
//        User user = list.get(0);
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("roles", user.roles().stream().map(role -> role).collect(Collectors.joining(",")))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + expirationTime))
//                .signWith(key)
//                .compact();
//    }
//
//    public boolean validateToken(String token){
//        Jwts.
//    }
//}
