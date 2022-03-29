package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.client.Address;
import com.cleverlance.academy.tofu.iotServer.model.client.Identification;
import com.cleverlance.academy.tofu.iotServer.model.client.Owner;
import com.cleverlance.academy.tofu.iotServer.repository.IdentificationRepository;
import com.cleverlance.academy.tofu.iotServer.service.mapper.IdentificationMapperForClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RestTemplate restTemplate;
    private final IdentificationMapperForClient identificationMapperForClient;
    private final IdentificationRepository identificationRepository;


    public RegistrationServiceImpl(RestTemplate restTemplate, IdentificationMapperForClient identificationMapperForClient, IdentificationRepository identificationRepository) {
        this.restTemplate = restTemplate;
        this.identificationMapperForClient = identificationMapperForClient;
        this.identificationRepository = identificationRepository;
    }


    @Override
    public void registerToAggregatorOnStartUp() {

        Identification identification = this.identificationMapperForClient.toIdentificationForClient(this.identificationRepository.getIdentification());
        String registrationUrl = "https://clv-iot-aggregator.herokuapp.com/identification";
        try {
            ResponseEntity response = this.restTemplate.postForEntity(registrationUrl, identification, Void.class);
            log.info("*** Registration of IOT server was finished with status code {} ***", response.getStatusCodeValue());
        } catch (RestClientResponseException restClientResponseException) {
            log.info("*** Registration of IOT server failed: {} ***", restClientResponseException.getStatusText());
        }
    }
}
