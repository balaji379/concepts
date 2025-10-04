package com.example.protobuffer.service;

import com.example.protobuffer.*;
import com.example.protobuffer.entity.AddressEntity;
import com.example.protobuffer.entity.Employees;
import com.example.protobuffer.repo.EmployeeRepo;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServices extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    @Autowired
    EmployeeRepo emyRepo;

    @Override
    public void getEmployeeById(Employee request, StreamObserver<Employee> responseObserver) {
        int id = request.getUserId();
        Employees employee = emyRepo.findById(id).get();
        AddressEntity addressEnity = employee.getAddress();

        Employee.Builder empBuilder = new Employee.Builder();
        Address.Builder addressBuilder = new Address.Builder();

//        setting hobbies
        for (String hobbie : employee.getHobbies()) empBuilder.addHobbies(hobbie);

        empBuilder
                .setActive(employee.isActive())
                .setName(employee.getName())
                .setUserId(employee.getUserId())
                .setAddress(
                        addressBuilder
                                .setStreet(addressEnity.getStreet())
                                .setCity(addressEnity.getCity())
                                .setState(addressEnity.getState())
                                .build()
                );
        responseObserver.onNext(empBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveEmployee(Employee request, StreamObserver<ResponseStatus> responseObserver) {
        Address address = request.getAddress();
        AddressEntity addressEntity = AddressEntity.builder()
                .street(address.getStreet())
                .state(address.getState())
                .city(address.getCity())
                .build();
        Employees employee = Employees.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .active(request.getActive())
                .hobbies(request.getHobbiesList())
                .address(addressEntity)
                .build();
        emyRepo.save(employee);
        responseObserver.onNext(ResponseStatus.newBuilder().setStatus("employee saved").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllEmployee(Empty request, StreamObserver<EmployeeList> responseObserver) {
        List<Employees> employees = emyRepo.findAll();
        EmployeeList.Builder employeeList = new EmployeeList.Builder();
        for (Employees employee : employees) {
            AddressEntity addressEnity = employee.getAddress();
            Employee.Builder empBuilder = new Employee.Builder();
            Address.Builder addressBuilder = new Address.Builder();
            Employee emp = empBuilder
                    .setActive(employee.isActive())
                    .setName(employee.getName())
                    .setUserId(employee.getUserId())
                    .setAddress(
                            addressBuilder
                                    .setStreet(addressEnity.getStreet())
                                    .setCity(addressEnity.getCity())
                                    .setState(addressEnity.getState())
                                    .build()
                    ).build();
            employeeList.addEmployee(emp);
        }
        responseObserver.onNext(employeeList.build());
        responseObserver.onCompleted();
    }
}
