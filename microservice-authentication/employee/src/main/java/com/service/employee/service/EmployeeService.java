package com.service.employee.service;

import com.service.employee.EmployeeApplication;
import com.service.employee.client.AddressClient;
import com.service.employee.modal.Address;
import com.service.employee.modal.Employee;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final AddressClient addressClient;
    List<Employee> employeeList = new ArrayList<>();

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

    public Employee getEmployee(int empId) {
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
    }
}
