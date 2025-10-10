package com.vb.grpc_jwt.service;


import com.google.protobuf.Empty;
import com.vb.grpc_jwt.Employee;
import com.vb.grpc_jwt.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import jdk.jfr.Percentage;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.access.prepost.PreAuthorize;

@GrpcService
public class GrpcEmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    @Override
    public void getEmployeeInfo(Empty request, StreamObserver<Employee> responseObserver) {

    }
}
