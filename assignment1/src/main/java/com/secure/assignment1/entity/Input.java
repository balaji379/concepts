package com.secure.assignment1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String date;
    private String conds;
    private double dewptm;
    private double fog;
    private double hail;
    private double heatindexm;
    private double hum;
    private double precipm;
    private double pressurem;
    private double rain;
    private double snow;
    private double tempm;
    private double thunder;
    private double tornado;
    private double vism;
    private double wdird;
    private String wdire;
    private double wgustm;
    private double windchillm;
    private double wspdm;
}
