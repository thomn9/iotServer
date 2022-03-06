package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;

import java.util.List;

public interface MeteorologicalDataRepository {

    void saveMeteorologicalData(MeteorologicalData meteorologicalData);

    List<MeteorologicalData> getMeteorologicalData();
}
