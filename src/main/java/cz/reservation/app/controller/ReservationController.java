package cz.reservation.app.controller;

import cz.reservation.app.model.dto.ReservationBaseDto;
import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservation")
public class ReservationController {
    //todo validation error propagation to API
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/detail")
    public ResponseEntity<ReservationDetailDto> getReservationDetail(@RequestParam String reservationCode) throws Exception {
        return ResponseEntity.ok(reservationService.getReservationDetail(reservationCode));
    }

    @PostMapping("/lock")
    public ResponseEntity<Void> lockReservation(@RequestParam Long reservableScheduleId) throws Exception {
        reservationService.lockReservableSchedule(reservableScheduleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationDetailDto> createReservation(@RequestBody ReservationBaseDto reservationBaseDto) throws Exception {
        return ResponseEntity.ok(reservationService.createReservation(reservationBaseDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReservation(@RequestParam String reservationCode) throws Exception {
        reservationService.deleteReservation(reservationCode);
        return ResponseEntity.ok().build();
    }


}
