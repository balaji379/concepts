package com.tutorial.grpc_employee.controller;

import com.tutorial.grpc_employee.rpc_client.AddressClient;
import com.tutorial.grpc_employee.rpc_service.EmployeeRpc;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    AddressClient addressClient;

    @GetMapping("/")
    public void bidirectionalStreaming(HttpServletResponse response, HttpServletRequest request) throws IOException {
        AsyncContext asyncContext = request.startAsync();
        OutputStream outputStream = response.getOutputStream();
        addressClient.sendEmployeeToAddressService(asyncContext,outputStream);
    }

}
