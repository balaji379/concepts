package com.tutorial.excel_batch_application.entity;

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
public class WeatherReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String datetimeUtc;
    private String conds;
    private Double dewptm;
    private Integer fog;
    private Integer hail;
    private Double heatindexm;
    private Integer hum;
    private Double precipm;
    private Double pressurem;
    private Integer rain;
    private Integer snow;
    private Double tempm;
    private Integer thunder;
    private Integer tornado;
    private Double vism;
    private Integer wdird;
    private String wdire;
    private Double wgustm;
    private Double windchillm;
    private Double wspdm;
}
