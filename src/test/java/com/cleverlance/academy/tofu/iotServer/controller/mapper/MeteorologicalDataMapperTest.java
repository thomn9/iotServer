package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//todo extend test for new properties (incl. id and timestamp)
class MeteorologicalDataMapperTest {

    private static final MeteorologicalDataMapper MAPPER = Mappers.getMapper(MeteorologicalDataMapper.class);

    @Test
    void toOpenApiMeteorologicalData() {
        MeteorologicalData meteorologicalData = new MeteorologicalData();
        meteorologicalData.setTemperature(7.5f);
        meteorologicalData.setHumidity(69.59f);

        org.openapitools.model.MeteorologicalDataWithTimeStampAndId convertedToOpenApiMeteorologicalData = MAPPER.toOpenApiMeteorologicalData(meteorologicalData);

        assertEquals(7.5f, convertedToOpenApiMeteorologicalData.getTemperature());
        assertEquals(69.59f, convertedToOpenApiMeteorologicalData.getHumidity());
    }

    @Test
    void toOpenApiListOdMeteorologicalData() {
        List<MeteorologicalData> meteorologicalDataList = new ArrayList<>();

        MeteorologicalData meteorologicalData1 = new MeteorologicalData();
        meteorologicalData1.setTemperature(15f);
        meteorologicalData1.setHumidity(78.01f);
        meteorologicalDataList.add(meteorologicalData1);

        MeteorologicalData meteorologicalData2 = new MeteorologicalData();
        meteorologicalData2.setTemperature(20.5f);
        meteorologicalData2.setHumidity(49.89f);
        meteorologicalDataList.add(meteorologicalData2);

        List<org.openapitools.model.MeteorologicalDataWithTimeStampAndId> convertedToOpenApiMeteorologicalDataList = MAPPER.toOpenApiListOdMeteorologicalData(meteorologicalDataList);

        assertEquals(15f, convertedToOpenApiMeteorologicalDataList.get(0).getTemperature());
        assertEquals(78.01f, convertedToOpenApiMeteorologicalDataList.get(0).getHumidity());
        assertEquals(20.5f, convertedToOpenApiMeteorologicalDataList.get(1).getTemperature());
        assertEquals(49.89f, convertedToOpenApiMeteorologicalDataList.get(1).getHumidity());
    }

    @Test
    void fromOpenApiMeteorologicalData() {
        org.openapitools.model.MeteorologicalDataBase openApiMeteorologicalData = new org.openapitools.model.MeteorologicalDataBase();
        openApiMeteorologicalData.setTemperature(12.01f);
        openApiMeteorologicalData.setHumidity(81f);

        MeteorologicalData convertedFromOpenApiMeteorologicalData = MAPPER.fromOpenApiMeteorologicalData(openApiMeteorologicalData);

        assertEquals(12.01f, convertedFromOpenApiMeteorologicalData.getTemperature());
        assertEquals(81f, convertedFromOpenApiMeteorologicalData.getHumidity());

    }


}