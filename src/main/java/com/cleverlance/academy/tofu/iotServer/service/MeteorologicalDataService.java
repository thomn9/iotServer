package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;

import java.util.List;

public interface MeteorologicalDataService {

    void saveMeteorologicalData(MeteorologicalData meteorologicalData);

    List<MeteorologicalData> getMeteorologicalData();
}
