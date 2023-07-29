package cz.reservation.app.controller;

import cz.reservation.app.model.dto.BusinessHoursDto;
import cz.reservation.app.model.dto.ServiceDefinitionBaseDto;
import cz.reservation.app.model.dto.ServiceDefinitionDto;
import cz.reservation.app.service.AdministrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AdministrationController {
    //todo validation error propagation to API
    @Autowired
    private AdministrationService administrationService;

    @GetMapping("/business-hours")
    public ResponseEntity<List<BusinessHoursDto>> getBusinessHours() {
        return ResponseEntity.ok(administrationService.getBusinessHours());
    }

    @PutMapping("/business-hours")
    public ResponseEntity<List<BusinessHoursDto>> setBusinessHours(@Valid @RequestBody List<BusinessHoursDto> businessHoursDtos) {
        return ResponseEntity.ok(administrationService.setBusinessHours(businessHoursDtos));
    }

    @GetMapping("/service-definitions")
    public ResponseEntity<List<ServiceDefinitionDto>> getServiceDefinitions() {
        return ResponseEntity.ok(administrationService.getServiceDefinitions());
    }

    @PostMapping("/service-definitions")
    public ResponseEntity<List<ServiceDefinitionDto>> createServiceDefinitions(@Valid @RequestBody ServiceDefinitionBaseDto serviceDefinitionBaseDto) {
        return ResponseEntity.ok(administrationService.createServiceDefinitions(serviceDefinitionBaseDto));
    }

    @DeleteMapping("/service-definitions")
    public ResponseEntity<List<ServiceDefinitionDto>> deleteServiceDefinitions(@RequestParam Long id) {
        return ResponseEntity.ok(administrationService.deleteServiceDefinitions(id));
    }

}
