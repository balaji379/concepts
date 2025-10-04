package com.secure.assignment1.controller;

import com.secure.assignment1.service.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/json-xml")
public class JsonToXmlController {

    @Autowired
    ConvertService convertService;

    @PostMapping
    public String convertJsonToXml(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(convertService.convertJsonFileToXml(file));
        return "success";
    }
}
