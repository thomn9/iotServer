package com.cleverlance.academy.tofu.iotServer.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "meteorological_data_records")
public class MeteorologicalData {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "temperature")
    private Float temperature;
    @Column(name = "humidity")
    private Float humidity;
    @Column(name = "light")
    private Float light;
    @Column(name = "pressure")
    private Float pressure;
    @Column(name = "timestamp")
    private OffsetDateTime timeStamp;

}
