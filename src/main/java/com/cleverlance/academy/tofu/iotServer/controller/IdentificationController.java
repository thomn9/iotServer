package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.controller.mapper.IdentificationMapper;
import com.cleverlance.academy.tofu.iotServer.service.IdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.openapitools.api.IdentificationApi;
import com.cleverlance.academy.tofu.iotServer.model.Identification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IdentificationController implements IdentificationApi {

    private static final IdentificationMapper MAPPER = Mappers.getMapper(IdentificationMapper.class);

    private final IdentificationService identificationService;
    public IdentificationController(IdentificationService identificationService) {
        this.identificationService = identificationService;
    }

   @Override
    public ResponseEntity<org.openapitools.model.Identification> getIdentification() {
        Identification identification = this.identificationService.getIdentification();
        return ResponseEntity.ok(MAPPER.toOpenApiIdentification(identification));
    }
}
