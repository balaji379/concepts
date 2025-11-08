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
@RequestMapping("/api/employee-service")
public class AddressController {

    private final AddressService employeeService;

<<<<<<< HEAD
    @GetMapping("/get-address/{id}")
    public Address getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/get_allAddress")

    public Address getEmployeeById(@PathVariable("id") int empId) {
=======
    @GetMapping("/get-employee/{id}")
    public Address getEmployeeById(@PathVariable int empId) {
>>>>>>> parent of 83ba36b (microservice-authentication)
        return employeeService.getEmployee(empId);
    }

    @GetMapping("/get-all-employee")
    public List<Address> getEmployee() {
        return employeeService.getAllEmplyee();
    }

}
