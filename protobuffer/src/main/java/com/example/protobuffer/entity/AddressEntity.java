package com.example.protobuffer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.protobuffer.entity.Employees;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String street;
    private String city;
    private String state;
    @OneToOne(mappedBy = "address")
    private Employees employee;
}
