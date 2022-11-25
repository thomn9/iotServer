package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.dto.BusinessHoursDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsBaseDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.DurationOfReservableTimeWindowsDto;
import com.cleverlance.academy.tofu.iotServer.service.AdministrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AdministrationController {

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

    @GetMapping("/duration-of-reservable-time-windows")
    public ResponseEntity<List<DurationOfReservableTimeWindowsDto>> getDurationsOfReservableTimeWindows() {
        return ResponseEntity.ok(administrationService.getDurationsOfReservableTimeWindows());
    }

    @PostMapping("/duration-of-reservable-time-windows")
    public ResponseEntity<List<DurationOfReservableTimeWindowsDto>> createDurationOfReservableTimeWindows(@Valid @RequestBody DurationOfReservableTimeWindowsBaseDto durationOfReservableTimeWindowsBaseDto) {
        return ResponseEntity.ok(administrationService.createDurationOfReservableTimeWindows(durationOfReservableTimeWindowsBaseDto));
    }

    @DeleteMapping("/duration-of-reservable-time-windows")
    public ResponseEntity<List<DurationOfReservableTimeWindowsDto>> deleteDurationOfReservableTimeWindows(@RequestParam Long id) {
        return ResponseEntity.ok(administrationService.deleteDurationOfReservableTimeWindows(id));
    }

}
