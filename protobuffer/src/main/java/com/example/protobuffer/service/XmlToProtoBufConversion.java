package com.example.protobuffer.service;

import com.example.protobuffer.Address;
import com.example.protobuffer.Employee;
import com.example.protobuffer.EmployeeOrBuilder;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@Service
public class XmlToProtoBufConversion {
    public List<String> xmlToProtoBufConversion(MultipartFile file) throws IOException {
        XmlMapper mapper = new XmlMapper();
        Map<String, ArrayList<Map<String, Object>>> root = mapper.readValue(file.getInputStream(), Map.class);
        System.out.println(root);
        List<String> employees = new ArrayList<>();
        for (Map<String, Object> map : root.get("employee")) {
            Employee.Builder empBuilder = new Employee.Builder();
            Address.Builder addBuilder = new Address.Builder();
            employees.add(mapObject(map, empBuilder, addBuilder).toString());
        }
        return employees;
        //return null;
    }

    private Employee mapObject(Map<String, Object> map, Employee.Builder buildEmployee, Address.Builder addressBuilder) {

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "userId" -> buildEmployee.setUserId(Integer.parseInt(value.toString()));
                case "name" -> buildEmployee.setName(value.toString());
                case "active" -> buildEmployee.setActive(Boolean.parseBoolean(value.toString()));

                case "hobbies" -> {
                    if (value instanceof List) {
                        for (Object hobbie : (List<?>) value) {
                            buildEmployee.addHobbies(hobbie.toString());
                        }
                    } else {
                        // single hobby as string
                        buildEmployee.addHobbies(value.toString());
                    }
                }

                case "address" -> {
                    Map<String, Object> addrMap = (Map<String, Object>) value;
                    addrMap.forEach((k, v) -> {
                        switch (k) {
                            case "street" -> addressBuilder.setStreet(v.toString());
                            case "city" -> addressBuilder.setCity(v.toString());
                            case "state" -> addressBuilder.setState(v.toString());
                        }
                    });
                    buildEmployee.setAddress(addressBuilder.build());
                }
            }
        }

        return buildEmployee.build();
    }

}
