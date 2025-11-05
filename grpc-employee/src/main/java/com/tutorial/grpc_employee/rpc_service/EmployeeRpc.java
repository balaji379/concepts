package com.tutorial.grpc_employee.rpc_service;

import com.tutorial.grpc_employee.*;
import com.tutorial.grpc_employee.Address;
import com.tutorial.grpc_employee.EmployeeListResponse;
import com.tutorial.grpc_employee.EmployeeProto;
import com.tutorial.grpc_employee.EmployeeRequest;
import com.tutorial.grpc_employee.EmployeeResponse;
import com.tutorial.grpc_employee.EmployeeServiceGrpc;
import com.tutorial.grpc_employee.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;


import java.util.ArrayList;
import java.util.List;

@GrpcService
public class EmployeeRpc extends EmployeeServiceGrpc.EmployeeServiceImplBase {



    List<EmployeeResponse> employeelist = new ArrayList<>();

    @PostConstruct
    public void buildEmployee() {
        for (int i = 0; i < 100; i++) {
            EmployeeProto employeeProto = EmployeeProto.newBuilder()
                    .setEmpId(i + 1)
                    .setEmpName("vignesh balaji " + (i + i))
                    .setAddress(Address.newBuilder().setEmpId(i + 1).setAddress("address for vignesh balaji " + (i + 1)).build())
                    .build();
            employeelist.add(EmployeeResponse.newBuilder().setEmployee(employeeProto).build());

        }
    }

    @Override
    public void getEmployee(EmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        int id = request.getEmpId();
        responseObserver.onNext(employeelist.get(id));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllEmployee(Empty request, StreamObserver<EmployeeResponse> responseObserver) {
        System.out.println("i have reached you but your status is unavailble");
        for (EmployeeResponse employeeResponse : employeelist) {
            responseObserver.onNext(employeeResponse);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }
}
