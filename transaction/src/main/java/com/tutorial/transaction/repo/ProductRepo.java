package com.tutorial.transaction.repo;

import com.tutorial.transaction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
