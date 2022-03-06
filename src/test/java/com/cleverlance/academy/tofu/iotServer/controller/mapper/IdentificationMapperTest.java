package com.cleverlance.academy.tofu.iotServer.controller.mapper;

import com.cleverlance.academy.tofu.iotServer.model.Address;
import com.cleverlance.academy.tofu.iotServer.model.Identification;
import com.cleverlance.academy.tofu.iotServer.model.Person;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationMapperTest {

    private static final IdentificationMapper MAPPER = Mappers.getMapper(IdentificationMapper.class);

    @Test
    void toIdentification() {
        Identification identification = new Identification(
                "Futomaki T.O.F.U.",
                new Address("Božská", "777", "Chotěboř"),
                new Person("Tomáš", "Hůlka")
        );

        org.openapitools.model.Identification converted = MAPPER.toOpenApiIdentification(identification);

        assertEquals("Futomaki T.O.F.U.", converted.getServerName());

        assertEquals("Božská", converted.getAddress().getStreet());
        assertEquals("777", converted.getAddress().getNumber());
        assertEquals("Chotěboř", converted.getAddress().getCity());

        assertEquals("Tomáš", converted.getOwner().getFirstName());
        assertEquals("Hůlka", converted.getOwner().getLastName());
    }
}