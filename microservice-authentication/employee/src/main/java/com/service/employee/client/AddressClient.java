package com.service.employee.client;

import com.service.employee.modal.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/api/address-service")
public interface AddressClient {

    @GetExchange("/get-address/{id}")
    public Address get_addressById(@PathVariable("id") int id);

    @GetExchange("/get-all-address")
    public List<Address> get_allAddress();

}
