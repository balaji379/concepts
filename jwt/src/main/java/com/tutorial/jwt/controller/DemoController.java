package com.tutorial.jwt.controller;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo-controller")
public class DemoController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/home")
    public ResponseEntity<String> hello(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ADMIN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you not have a permission to access this resource");

        return ResponseEntity.ok().body("hello this is vignesh balaji");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/another-home")
    public ResponseEntity<String> anotherHome(){
        return ResponseEntity.ok().body("hello this is another home");
    }
}
