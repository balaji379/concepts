package com.service.employee.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface AddressClient {

    @GetExchange("/api/address-service/get-address")
    public List<Address> get_address();

}
