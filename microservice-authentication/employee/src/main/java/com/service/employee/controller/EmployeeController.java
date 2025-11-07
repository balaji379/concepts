package com.service.employee.controller;

import com.service.employee.modal.Employee;
import com.service.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee-service")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-employee/{id}")
    public Employee getEmployeeById(@PathVariable int empId) {
        return employeeService.getEmployee(empId);
    }

    @GetMapping("/get-all-employee")
    public List<Employee> getEmployee() {
        return employeeService.getAllEmplyee();
    }

}
