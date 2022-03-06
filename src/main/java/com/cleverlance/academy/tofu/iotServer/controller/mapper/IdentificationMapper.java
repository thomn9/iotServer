package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.Identification;

@Mapper
public interface IdentificationMapper {

    @Mapping(target = "serverName", source = "name")
    Identification toOpenApiIdentification(com.cleverlance.academy.tofu.iotServer.model.Identification identification);
}
