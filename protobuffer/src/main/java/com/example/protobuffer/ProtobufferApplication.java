package com.example.protobuffer;

import com.example.protobuffer.Controller.EmployeeService;
import com.example.protobuffer.service.GrpcServices;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication

public class ProtobufferApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ProtobufferApplication.class, args);
		Server server = ServerBuilder.forPort(9090).addService(new EmployeeService()).build();
		server.start();
		System.out.println("grpc server is activated welcome");
	}
}