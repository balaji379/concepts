package com.secure.assignment1.controller;

import com.secure.assignment1.entity.Input;
import com.secure.assignment1.repo.InputRepo;
import com.secure.assignment1.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InputController {
    @Autowired
    ExcelService excelService;

    @Autowired
    InputRepo repo;

    @GetMapping
    public String storeData (){
         excelService.readData("testset");
         return "success";
    }
    @GetMapping("/get-data/{id}")
    public Input getData(@PathVariable("id") int id){
        return repo.findById(id).get();
    }
}
