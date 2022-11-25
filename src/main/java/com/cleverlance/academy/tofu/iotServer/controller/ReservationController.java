package com.cleverlance.academy.tofu.iotServer.controller;

import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataForADateDto;
import com.cleverlance.academy.tofu.iotServer.model.dto.ReservationDataDetailDto;
import com.cleverlance.academy.tofu.iotServer.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReservationController {
    //todo validation error propagation to API
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservable-time-windows-schedule/{durationOfReservableTimeWindowId}")
    public ResponseEntity<List<ReservationDataForADateDto>> getReservableTimeWindowsSchedule(@PathVariable Long durationOfReservableTimeWindowId) {
        return ResponseEntity.ok(reservationService.getReservableTimeWindowsSchedule(durationOfReservableTimeWindowId));
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationDataDetailDto> createReservation(@Valid @RequestBody ReservationDataDto reservationDataDto) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDataDto));
    }

    @GetMapping("/reservation-detail")
    public ResponseEntity<ReservationDataDetailDto> getReservationDetail(@RequestParam String reservationCode) {
        return ResponseEntity.ok(reservationService.getReservationDetail(reservationCode));
    }

    @DeleteMapping ("/delete-reservation")
    public ResponseEntity<Void> deleteReservation(@RequestParam String reservationCode) {
        reservationService.deleteReservation(reservationCode);
        return ResponseEntity.ok().build();
    }
}
