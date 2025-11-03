package com.tutorial.transaction.model;

import lombok.Builder;

@Builder
public record ProductDto(
        int id,
        String name,
        double price,
        int stockQuantity
) {
}
