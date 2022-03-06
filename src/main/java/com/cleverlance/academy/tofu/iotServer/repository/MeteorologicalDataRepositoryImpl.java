package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MeteorologicalDataRepositoryImpl implements MeteorologicalDataRepository {

    private final List<MeteorologicalData> meteorologicalDataList = new ArrayList<>();

    @Override
    public void saveMeteorologicalData(MeteorologicalData meteorologicalData) {
        this.meteorologicalDataList.add(meteorologicalData);
    }

    @Override
    public List<MeteorologicalData> getMeteorologicalData() {
        return this.meteorologicalDataList;
    }
}
