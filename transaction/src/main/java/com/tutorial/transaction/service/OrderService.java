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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    /*
    * propagation ->
    * Propagation.REQUIRED -> A single main transaction handles the all inner transaction if any of one is failed ,then it rollback the ever inner transaction
    * Propagation.REQUIRED_NEW -> it create a  new transaction even it has main transaction and it will execute even any inner transaction is failed
    * Propagation.MANDATORY -> it check if any transaction has present ,then it use that else throw exception and if any error occur while the execution it not harm the main transaction
    * Propagation.NEVER -> it execute without any transaction, throw exception if any transaction is found
    * Propagation.NOT_SUPPORTED -> execute the block of without any transaction , suspend the transaction if any transaction exist
    * Propagation.NESTED -> it similar to required_new but if outer transaction push it to roll back when that failed.
    *  */
    /*
    * Isolation -> it define how be visibility of changes between the transaction
    * Isolation.DEFAULT -> it select the isolation based on data base what default isolation of database
    * Isolation.READ_UNCOMMIT -> it allow a thread to read data even that data using by other so it lead to make dirty data
    * Isolation.READ_COMMIT -> it not allow a thread to read uncommited data it only give last commited data
    * Isolation.Serialization -> it make a con-currency execution to synchronous execution like one after other
    * */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void placeOrder(Order order) {
        int orderId = order.getProductId();
        if (productRepo.existsById(orderId)) {
            Product product = productRepo.getReferenceById(orderId);
            if (product.getStockQuantity() >= order.getQuantity()) {
                double amount = order.getQuantity() * product.getPrice();
                order.setTotalPrice(amount);
                product.setStockQuantity(product.getStockQuantity() - order.getQuantity());
                orderRepo.save(order);
                productRepo.save(product);
            } else throw new RuntimeException("Stack is unavailable");
        } else throw new RuntimeException("Stock is unavailable");
    }
}
