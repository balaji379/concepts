package com.tutorial.transaction.model;

public record OrderDto(
        int id,
        int productId,
        int quantity,
        double totalPrice
        ) {
}
