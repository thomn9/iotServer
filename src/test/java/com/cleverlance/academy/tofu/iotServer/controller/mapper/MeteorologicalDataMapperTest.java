package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class MeteorologicalDataMapperTest {

    private static final MeteorologicalDataMapper MAPPER = Mappers.getMapper(MeteorologicalDataMapper.class);

    @Test
    void toOpenApiMeteorologicalData() {
        MeteorologicalData meteorologicalData = new MeteorologicalData(7.5f,69.59f);

        org.openapitools.model.MeteorologicalData convertedToOpenApiMeteorologicalData = MAPPER.toOpenApiMeteorologicalData(meteorologicalData);

        assertEquals(7.5f, convertedToOpenApiMeteorologicalData.getTemperature());
        assertEquals(69.59f, convertedToOpenApiMeteorologicalData.getHumidity());
    }


    @Test
    void fromOpenApiMeteorologicalData() {
        org.openapitools.model.MeteorologicalData openApiMeteorogicalData = new org.openapitools.model.MeteorologicalData();
        openApiMeteorogicalData.setTemperature(12.01f);
        openApiMeteorogicalData.setHumidity(81f);

        MeteorologicalData convertedFromOpenApiMeteorologicalData = MAPPER.fromOpenApiMeteorologicalData(openApiMeteorogicalData);

        assertEquals(12.01f, convertedFromOpenApiMeteorologicalData.getTemperature());
        assertEquals(81f, convertedFromOpenApiMeteorologicalData.getHumidity());

    }
}