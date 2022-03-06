package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.controller.mapper.MeteorologicalDataMapper;
import com.cleverlance.academy.tofu.iotServer.service.MeteorologicalDataService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.openapitools.api.MeteorologicalDataApi;
import org.openapitools.model.MeteorologicalData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class MeteorologicalDataController implements MeteorologicalDataApi {

    private static final MeteorologicalDataMapper MAPPER = Mappers.getMapper(MeteorologicalDataMapper.class);

    private final MeteorologicalDataService meteorologicalDataService;

    public MeteorologicalDataController(MeteorologicalDataService meteorologicalDataService) {
        this.meteorologicalDataService = meteorologicalDataService;
    }

    @Override
    public ResponseEntity<List<MeteorologicalData>> getMeteorologicalData() {
        List<MeteorologicalData> mappedMeteorologicalDataList =
                this.meteorologicalDataService.getMeteorologicalData().stream()
                        .map(meteorologicalData -> MAPPER.toOpenApiMeteorologicalData(meteorologicalData))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(mappedMeteorologicalDataList);
    }

    @Override
    public ResponseEntity<Void> saveMeteorologicalData(@RequestBody MeteorologicalData meteorologicalData) {
        this.meteorologicalDataService.saveMeteorologicalData(MAPPER.fromOpenApiMeteorologicalData(meteorologicalData));
        return ResponseEntity.ok().build();
    }
}
