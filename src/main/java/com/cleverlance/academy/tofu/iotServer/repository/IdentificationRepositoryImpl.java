package com.cleverlance.academy.tofu.iotServer.repository;

import com.cleverlance.academy.tofu.iotServer.model.Address;
import com.cleverlance.academy.tofu.iotServer.model.Identification;
import com.cleverlance.academy.tofu.iotServer.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public class IdentificationRepositoryImpl implements IdentificationRepository {
    @Override
    public Identification getIdentification() {
        return new Identification(
                "Futomaki T.O.F.U.",
                new Address("Božská", "777", "Chotěboř"),
                new Person("Tomáš", "Hůlka")
        );
    }
}
