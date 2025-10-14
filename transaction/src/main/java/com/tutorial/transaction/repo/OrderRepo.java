package com.tutorial.transaction.repo;

import com.tutorial.transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
