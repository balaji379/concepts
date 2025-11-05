package com.tutorial.grpc_address.rpcservice;

import com.tutorial.grpc_employee.Address;
import com.tutorial.grpc_employee.AddressRequest;
import com.tutorial.grpc_employee.AddressServiceGrpc;
import com.tutorial.grpc_employee.EmployeeProto;
import com.tutorial.grpc_employee.Empty;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.List;


@GrpcService
public class AddressRpcService extends AddressServiceGrpc.AddressServiceImplBase {
    List<Address> addressList = new ArrayList<>();

    @PostConstruct
    public void generateListOfAddress() {
        for (int i = 0; i < 100; i++) {
            addressList.add(
                    Address.newBuilder()
                            .setEmpId(i + 1)
                            .setAddress("address for employee " + (i + 1))
                            .build()
            );
        }
    }

    @Override
    public void getAddressById(AddressRequest request, StreamObserver<Address> responseObserver) {
        responseObserver.onNext(addressList.get(request.getEmpId() + 1));
        responseObserver.onCompleted();
    }

    @Override
    public void getListOfAddress(Empty request, StreamObserver<com.tutorial.grpc_employee.AddressAllResponse> responseObserver) {
        var addressListBuilder = com.tutorial.grpc_employee.AddressAllResponse.newBuilder();
        for (Address address : addressList) {
            addressListBuilder.addAddress(address);
        }
        responseObserver.onNext(addressListBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAddress(Empty request, StreamObserver<Address> responseObserver) {
        for (Address address : addressList) {
            responseObserver.onNext(address);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<com.tutorial.grpc_employee.EmployeeProto> sendListEmployeeToAddress(StreamObserver<EmployeeProto> responseObserver) {
        return new StreamObserver<EmployeeProto>() {
            @Override
            public void onNext(EmployeeProto employeeProto) {
                System.out.println(employeeProto);
                responseObserver.onNext(employeeProto.toBuilder()
                        .setAddress(addressList.get(employeeProto.getEmpId() - 1))
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println(throwable.getCause());
            }

            @Override
            public void onCompleted() {
                System.out.println("i have sent all response to you my side has completed");
                responseObserver.onCompleted();
            }
        };
    }
}

