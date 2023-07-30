package cz.reservation.app.controller;

import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    //todo validation error propagation to API
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation-detail")
    public ResponseEntity<ReservationDetailDto> getReservationDetail(@RequestParam String reservationCode) throws Exception {
        return ResponseEntity.ok(reservationService.getReservationDetail(reservationCode));
    }

}
