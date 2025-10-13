package com.stream.output_service.cofig;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OPenaiConfig {

    @Bean
    public OpenAPI setInfo(){
        Server devserver = new Server();
        devserver.setDescription("movie streaming application");
        devserver.setUrl("http://localhost:8080");

        Server pserver = new Server();
        pserver.setDescription("movie streaming application");
        pserver.setUrl("http://localhost:8081");



        Contact contact = new Contact();
        contact.setName("vignesh balaji");
        contact.setUrl("http://localhost:8080/email");
        contact.setEmail("vigneshbalaji.kvl@gmail.com");

        Info info = new Info();
        info.setDescription("movie streaming web application");
        info.setContact(contact);
        info.title("Video playlist");
        info.version("1.0");

        return new OpenAPI().info(info).servers(List.of(devserver,pserver));

    }
}
