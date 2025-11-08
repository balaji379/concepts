package com.concept.gateway.service;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    private final List<String> openEndPoints = List.of("/api/auth/**");

    public Predicate<ServerHttpRequest> validate =
            request -> openEndPoints
                    .stream()
                    .noneMatch(url -> request.getURI().getPath().contains(url));
}
