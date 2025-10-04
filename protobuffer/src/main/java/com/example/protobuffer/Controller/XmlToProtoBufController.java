package com.example.protobuffer.Controller;

//import com.example.protobuffer.Employee;
import com.example.protobuffer.service.XmlToProtoBufConversion;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/xml-conversion")
public class XmlToProtoBufController {


    @PostMapping
    public List<String> xmlToProtoBuf(@RequestBody MultipartFile file) throws IOException {
      //  List<String> employee =  conversion.xmlToProtoBufConversion(file);
      //  System.out.println("tostring: "+employee.toString());
        return new ArrayList<>();
    }
}
