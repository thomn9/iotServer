package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import com.cleverlance.academy.tofu.iotServer.repository.MeteorologicalDataRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MeteorologicalDataServiceImpl implements MeteorologicalDataService{

    private final MeteorologicalDataRepository meteorologicalDataRepository;
    public MeteorologicalDataServiceImpl(MeteorologicalDataRepository meteorologicalDataRepository) {
        this.meteorologicalDataRepository = meteorologicalDataRepository;
    }

    @Override
    public void saveMeteorologicalData(MeteorologicalData meteorologicalData) {
        meteorologicalData.setTimeStamp(OffsetDateTime.now());
        this.meteorologicalDataRepository.save(meteorologicalData);
    }

    @Override
    public List<MeteorologicalData> getMeteorologicalData() {
        return this.meteorologicalDataRepository.findAll();
    }
}
