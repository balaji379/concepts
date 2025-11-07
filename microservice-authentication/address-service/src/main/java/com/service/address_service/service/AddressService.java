package com.service.address_service.service;

import com.service.address_service.modal.Address;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    List<Address> addressList = new ArrayList<>();

    @PostConstruct
    public void generateEmployee() {
        for (int i = 0; i < 100; i++) {
               addressList.add(
                       Address.builder()
                               .empId(i + 1)
                               .address("vignesh balaji address for " + (i + 1))
                               .build()
               );
        }
    }
    public Address getEmployee(int empId){
        return addressList.get(empId - 1);
    }

    public List<Address> getAllEmplyee(){
        return addressList;
    }
}
