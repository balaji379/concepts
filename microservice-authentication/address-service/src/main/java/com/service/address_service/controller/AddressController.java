package com.service.address_service.controller;

import com.service.address_service.modal.Address;
import com.service.address_service.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address-service")
public class AddressController {

    private final AddressService employeeService;

    @GetMapping("/get-address/{id}")
    public Address getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/get_allAddress")
    public List<Address> getEmployee() {
        return employeeService.getAllEmplyee();
    }

}
