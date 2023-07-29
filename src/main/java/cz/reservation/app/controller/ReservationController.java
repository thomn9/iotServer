package cz.reservation.app.controller;

import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservableTimeWindowForADateDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.service.ReservationService;
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
    public ResponseEntity<List<ReservableTimeWindowForADateDto>> getReservableTimeWindowsSchedule(@PathVariable Long durationOfReservableTimeWindowId) {
        return ResponseEntity.ok(reservationService.getReservableTimeWindowsSchedule(durationOfReservableTimeWindowId));
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationDetailDto> createReservation(@Valid @RequestBody ReservationBaseDto reservationBaseDto) {
        return ResponseEntity.ok(reservationService.createReservation(reservationBaseDto));
    }

    @GetMapping("/reservation-detail")
    public ResponseEntity<ReservationDetailDto> getReservationDetail(@RequestParam String reservationCode) {
        return ResponseEntity.ok(reservationService.getReservationDetail(reservationCode));
    }

    @DeleteMapping ("/delete-reservation")
    public ResponseEntity<Void> deleteReservation(@RequestParam String reservationCode) {
        reservationService.deleteReservation(reservationCode);
        return ResponseEntity.ok().build();
    }
}
