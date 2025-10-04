package com.example.protobuffer.Controller;

import com.example.protobuffer.Address;
import com.example.protobuffer.Employee;
import com.example.protobuffer.EmployeeServiceGrpc;
import com.example.protobuffer.ResponseStatus;
import com.example.protobuffer.entity.AddressEntity;
import com.example.protobuffer.entity.Employees;
import com.example.protobuffer.repo.EmployeeRepo;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    EmployeeRepo emyRepo;

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
}
