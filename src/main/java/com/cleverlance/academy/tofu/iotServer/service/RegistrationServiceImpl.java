package com.cleverlance.academy.tofu.iotServer.service;


import com.cleverlance.academy.tofu.iotServer.client.api.IdentificationApi;
import com.cleverlance.academy.tofu.iotServer.client.model.Identification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final IdentificationApi identificationApi;

    public RegistrationServiceImpl(IdentificationApi identificationApi) {
        this.identificationApi = identificationApi;
    }


    @Override
    public void registerToAggregatorOnStartUp() {

        List<Identification> response = (List<Identification>) this.identificationApi.getIdentifications1(10,0);
        StringBuilder sb = new StringBuilder();
        for (Identification s : response)
        {
            sb.append(s.toString());
            sb.append("\t");
        }


        log.info("Reply from aggregator {}", sb.toString() );
        ;

    }
}
