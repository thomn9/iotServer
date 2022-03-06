package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import org.mapstruct.Mapper;
import org.openapitools.model.MeteorologicalData;

@Mapper
public interface MeteorologicalDataMapper {

    MeteorologicalData toOpenApiMeteorologicalData(com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData meteorologicalData);
    com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData fromOpenApiMeteorologicalData(MeteorologicalData meteorologicalData);

}
