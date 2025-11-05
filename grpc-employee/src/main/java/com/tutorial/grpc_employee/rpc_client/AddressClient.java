package com.tutorial.grpc_employee.rpc_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.grpc_employee.*;
import com.tutorial.grpc_employee.modal.AddressModal;
import com.tutorial.grpc_employee.modal.EmployeeModal;
import com.tutorial.grpc_employee.rpc_service.EmployeeRpc;
import io.grpc.stub.StreamObserver;
import jakarta.servlet.AsyncContext;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AddressClient {

    @GrpcClient("address-client")
    AddressServiceGrpc.AddressServiceBlockingStub address_service;

    @GrpcClient("address-client")
    AddressServiceGrpc.AddressServiceStub asynAddress_service;

    @Autowired
    EmployeeRpc employeeRpc;

    public Address getAddressById(int empId) {
        AddressRequest request = AddressRequest.newBuilder().setEmpId(empId).build();
        Address response = address_service.getAddressById(request);
        System.err.println(response);
        return response;
    }

    public void getstreamOfAddress(List<EmployeeResponse> employeeResponses, StreamObserver<EmployeeResponse> responseStreamObserver) {
        Iterator<Address> addressIterator = address_service.getAllAddress(Empty.newBuilder().build());
        while (addressIterator.hasNext()) {
            Address address = addressIterator.next();
            int empId = address.getEmpId();
            EmployeeProto employeeProto = employeeResponses.get(empId - 1).getEmployee();
            EmployeeProto newEmployee = employeeProto.toBuilder()
                    .setAddress(address)
                    .build();
            responseStreamObserver.onNext(EmployeeResponse.newBuilder().setEmployee(newEmployee).build());
        }
        responseStreamObserver.onCompleted();
    }

    public EmployeeListResponse getstreamOfAddressList(List<EmployeeResponse> employeeResponses) {
        var responseBuilder = EmployeeListResponse.newBuilder();
        Iterator<Address> addressIterator = address_service.getAllAddress(Empty.newBuilder().build());
        while (addressIterator.hasNext()) {
            Address address = addressIterator.next();
            int empId = address.getEmpId();
            EmployeeProto employeeProto = employeeResponses.get(empId - 1).getEmployee();
            EmployeeProto newEmployee = employeeProto.toBuilder()
                    .setAddress(address)
                    .build();

            responseBuilder.addEmployeeList(newEmployee);
        }
        return responseBuilder.build();
    }

    public void sendEmployeeToAddressService(AsyncContext asyncContext, OutputStream outputStream) {
        StreamObserver<EmployeeProto> request =
                asynAddress_service.sendListEmployeeToAddress(new StreamObserver<EmployeeProto>() {
                    @Override
                    public void onNext(EmployeeProto employeeProto) {
                        try {
                            EmployeeModal employeeModal = EmployeeModal.builder()
                                    .empId(employeeProto.getEmpId())
                                    .name(employeeProto.getEmpName())
                                    .address(AddressModal.builder()
                                            .empId(employeeProto.getEmpId())
                                            .address(employeeProto.getAddress().getAddress())
                                            .build())
                                    .build();
                            ObjectMapper mapper = new ObjectMapper();
                            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                            String json = writer.writeValueAsString(employeeModal);
                            outputStream.write(json.getBytes());
                            outputStream.flush();
                            Thread.sleep(500);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.err.println(employeeProto);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println(throwable.getCause());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("response stream has completed");
                        asyncContext.complete();
                    }
                });
        for (EmployeeResponse employeeProto : employeeRpc.getEmployeeProto()) {
            System.out.println(employeeProto.getEmployee());
            request.onNext(employeeProto.getEmployee());
        }
        request.onCompleted();
    }


}
