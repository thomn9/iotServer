package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import com.cleverlance.academy.tofu.iotServer.repository.JpaMeteorologicalDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeteorologicalDataServiceImpl implements MeteorologicalDataService{

    private final JpaMeteorologicalDataRepository meteorologicalDataRepository;

    public MeteorologicalDataServiceImpl(JpaMeteorologicalDataRepository meteorologicalDataRepository) {
        this.meteorologicalDataRepository = meteorologicalDataRepository;
    }

    @Override
    public void saveMeteorologicalData(MeteorologicalData meteorologicalData) {
        meteorologicalData.setTimeStamp(OffsetDateTime.now());
        this.meteorologicalDataRepository.save(meteorologicalData);
    }

    @Override
    public List<MeteorologicalData> getMeteorologicalData(Integer pageSize, Integer pageNumber) {
        Page<MeteorologicalData> result = this.meteorologicalDataRepository.findAll(PageRequest.of(pageNumber,pageSize));
        return result.get().collect(Collectors.toList());
    }
}
