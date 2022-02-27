package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.Address;
import com.cleverlance.academy.tofu.iotServer.model.Identification;
import com.cleverlance.academy.tofu.iotServer.model.MeteorologicalData;
import com.cleverlance.academy.tofu.iotServer.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class AgreggatorController {

    private final List<MeteorologicalData> meteorologicalDataList = new ArrayList<>();

    @GetMapping(path = "/identification")
    public ResponseEntity<Identification> getIdentification() {
        return ResponseEntity.ok(
                new Identification(
                        "Futomaki T.O.F.U.",
                        new Address("Božská", "777", "Chotěboř"),
                        new Person("Tomáš", "Hůlka")
                        )
        );
    }

    @PostMapping("/meteorological-data")
    public ResponseEntity<Void> saveMeteorologicalData(@RequestBody MeteorologicalData meteorologicalData){
        this.meteorologicalDataList.add(meteorologicalData);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/meteorological-data")
    public ResponseEntity<List<MeteorologicalData>> getMeteorologicalData() {
        return ResponseEntity.ok(this.meteorologicalDataList);
    }
}
