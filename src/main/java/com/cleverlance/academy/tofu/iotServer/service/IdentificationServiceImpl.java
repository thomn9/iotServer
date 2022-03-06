package com.cleverlance.academy.tofu.iotServer.service;

import com.cleverlance.academy.tofu.iotServer.model.Identification;
import com.cleverlance.academy.tofu.iotServer.repository.IdentificationRepository;
import org.springframework.stereotype.Service;

@Service
public class IdentificationServiceImpl implements IdentificationService {

    private final IdentificationRepository identificationRepository;

    public IdentificationServiceImpl(IdentificationRepository identificationRepository) {
        this.identificationRepository = identificationRepository;
    }

    @Override
    public Identification getIdentification() {
        return this.identificationRepository.getIdentification();
    }

}
