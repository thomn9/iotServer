package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.Address;
import com.cleverlance.academy.tofu.iotServer.model.Identification;
import com.cleverlance.academy.tofu.iotServer.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AgreggatorController {

    @GetMapping(path = "/identification")
    public ResponseEntity<Identification> getIdentification() {
        return ResponseEntity.ok(
                new Identification(
                        "Tofu meteostanice",
                        new Address("Božská", "777", "Chotěboř"),
                        new Person("Tomáš", "Hůlka")
                        )
        );
    }
}
