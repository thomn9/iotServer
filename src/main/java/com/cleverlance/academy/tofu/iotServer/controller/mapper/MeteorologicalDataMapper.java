package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.openapitools.model.MeteorologicalDataBase;
import org.openapitools.model.MeteorologicalDataWithTimeStampAndId;

import java.util.List;

@Mapper
public interface MeteorologicalDataMapper {

    @Mapping(target = "timestamp", source = "timeStamp")
    MeteorologicalDataWithTimeStampAndId toOpenApiMeteorologicalData(com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData meteorologicalData);

    List<MeteorologicalDataWithTimeStampAndId> toOpenApiListOdMeteorologicalData(List<com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData> meteorologicalDataList);
    com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData fromOpenApiMeteorologicalData(MeteorologicalDataBase meteorologicalData);

}
