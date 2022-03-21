package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.controller.mapper.MeteorologicalDataMapper;
import com.cleverlance.academy.tofu.iotServer.service.MeteorologicalDataService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.openapitools.api.MeteorologicalDataApi;
import org.openapitools.model.MeteorologicalDataBase;
import org.openapitools.model.MeteorologicalDataWithTimeStampAndId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class MeteorologicalDataController implements MeteorologicalDataApi {

    private static final MeteorologicalDataMapper MAPPER = Mappers.getMapper(MeteorologicalDataMapper.class);

    private final MeteorologicalDataService meteorologicalDataService;

    public MeteorologicalDataController(MeteorologicalDataService meteorologicalDataService) {
        this.meteorologicalDataService = meteorologicalDataService;
    }

    @Override
    public ResponseEntity<List<MeteorologicalDataWithTimeStampAndId>> getMeteorologicalData(Integer pageSize, Integer pageNumber) {
        List<MeteorologicalDataWithTimeStampAndId> mappedMeteorologicalDataList = MAPPER.toOpenApiListOdMeteorologicalData(this.meteorologicalDataService.getMeteorologicalData(pageSize,pageNumber));
        return ResponseEntity.ok(mappedMeteorologicalDataList);
    }

    @Override
    public ResponseEntity<Void> saveMeteorologicalData(@Valid @RequestBody MeteorologicalDataBase meteorologicalData) {
        this.meteorologicalDataService.saveMeteorologicalData(MAPPER.fromOpenApiMeteorologicalData(meteorologicalData));
        return ResponseEntity.ok().build();
    }
}
