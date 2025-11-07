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

    public Employee getEmployee(int empId){
        return employeeList.get(empId - 1);
    }

    public List<Employee> getAllEmplyee(){
        return employeeList;
    }



}
