package com.service.employee.service;

import com.service.employee.EmployeeApplication;
import com.service.employee.modal.Address;
import com.service.employee.modal.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    List<Employee> employeeList = new ArrayList<>();
    private final AddressClient addressClient;

    @PostConstruct
    public void generateEmployee() {
        for (int i = 0; i < 100; i++) {
            employeeList.add(
                    Employee.builder()
                            .id(i + 1)
                            .name("vignesh balaji " + (i + 1))
                            .department(i % 2 == 0 ? "IT" : "CSE")
                            .build()
            );
        }
    }

<<<<<<< HEAD
    public Employee getEmployee(int empId) {

        Employee employee = employeeList.get(empId - 1);
        Address address = addressClient.get_addressById(empId);
        return Employee.builder()
                .address(address)
                .id(empId)
                .name(employee.name())
                .department(employee.department())
                .build();
    }

    public List<Employee> getAllEmplyee() {
        List<Address> addressList = addressClient.getAllAddress();
        Iterator<Address> addressIterator = addressList.iterator();
        return employeeList.stream().map(e -> {
            return Employee.builder()
                    .address(addressIterator.next())
                    .name(e.name())
                    .id(e.id())
                    .department(e.department())
                    .build();

        Employee emp = employeeList.get(empId);
        return
                Employee.builder()
                        .name(emp.name())
                        .id(empId)
                        .department(emp.department())
                        .address(addressClient.get_addressById(empId))
                        .build();

    }

    public List<Employee> getAllEmplyee() {
        Iterator<Address> addressIterator = addressClient.get_allAddress().iterator();
        return employeeList.stream().map(e -> {
            return Employee.builder()
                    .name(e.name())
                    .id(e.id())
                    .department(e.department())
                    .address(addressIterator.next())
                    .build();
        }).toList();
=======
    public Employee getEmployee(int empId){
        return employeeList.get(empId - 1);
    }

    public List<Employee> getAllEmplyee(){
        return employeeList;
>>>>>>> parent of 83ba36b (microservice-authentication)
    }



}
