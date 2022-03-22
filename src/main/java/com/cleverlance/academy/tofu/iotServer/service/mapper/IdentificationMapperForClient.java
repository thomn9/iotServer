package com.cleverlance.academy.tofu.iotServer.service.mapper;

import com.cleverlance.academy.tofu.iotServer.model.Person;
import com.cleverlance.academy.tofu.iotServer.model.client.Address;
import com.cleverlance.academy.tofu.iotServer.model.client.Identification;
import com.cleverlance.academy.tofu.iotServer.model.client.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IdentificationMapperForClient {

    @Mapping(target = "url", constant = "https://tofu-iot-server.herokuapp.com")
    Identification toIdentificationForClient(com.cleverlance.academy.tofu.iotServer.model.Identification identification);

    @Mapping(target = "streetNumber", source = "number")
    Address toAddressForClient(com.cleverlance.academy.tofu.iotServer.model.Address address);

    @Mapping(target = "email" , constant = "email@konstatna.cz")
    Owner toOwnerForClient(Person person);

}
