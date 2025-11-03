package com.tutorial.jwt.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record User(
        String username,
        String password,
        Set<String> roles
) {
}
