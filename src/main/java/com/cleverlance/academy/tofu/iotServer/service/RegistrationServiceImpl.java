package com.cleverlance.academy.tofu.iotServer.service;


import com.cleverlance.academy.aggregator.client.api.IdentificationApi;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final com.cleverlance.academy.aggregator.client.api.IdentificationApi identificationApi;

    public RegistrationServiceImpl(IdentificationApi identificationApi) {
        this.identificationApi = identificationApi;
    }


    @Override
    public void registerToAggregatorOnStartUp() {

    }
}
