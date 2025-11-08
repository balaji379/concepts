package com.service.employee.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface AddressClient {

<<<<<<< HEAD
    @GetExchange("/get-address/{id}")
    public Address get_addressById(@PathVariable("id") int id);


    @GetExchange("/get_allAddress")
    public List<Address> getAllAddress();

    @GetExchange("/get-all-address")
    public List<Address> get_allAddress();
=======
    @GetExchange("/api/address-service/get-address")
    public List<Address> get_address();
>>>>>>> parent of 83ba36b (microservice-authentication)


}
