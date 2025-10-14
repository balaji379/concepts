package com.tutorial.transaction.service;

import com.tutorial.transaction.entity.Order;
import com.tutorial.transaction.entity.Product;
import com.tutorial.transaction.model.OrderDto;
import com.tutorial.transaction.model.ProductDto;
import com.tutorial.transaction.repo.OrderRepo;
import com.tutorial.transaction.repo.ProductRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductRepo productRepo;

    public void saveOrder(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        orderRepo.save(order);
    }

    public ProductDto findById(int id) {

        Product product = productRepo.findById(id).get();
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .id(product.getId())
                .build();
    }
}
