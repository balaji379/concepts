package com.tutorial.transaction.service;

import com.tutorial.transaction.model.ProductDto;
import com.tutorial.transaction.repo.OrderRepo;
import com.tutorial.transaction.repo.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessInventory {

    ProductRepo productRepo;
    OrderRepo orderRepo;

    OrderProcessInventory(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;

    }
}
