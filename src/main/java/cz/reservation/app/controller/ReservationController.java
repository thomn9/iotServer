package cz.reservation.app.controller;

import cz.reservation.app.model.dto.ReservationDetailDto;
import cz.reservation.app.service.WSNotificationService;
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

    @Autowired
    private WSNotificationService wsNotificationService;
    @GetMapping("/detail")
    public ResponseEntity<ReservationDetailDto> getReservationDetail(@RequestParam String reservationCode) throws Exception {
        return ResponseEntity.ok(reservationService.getReservationDetail(reservationCode));
    }

    @PostMapping("/lock")
    public ResponseEntity<Void> lockReservation(@RequestParam Long reservableScheduleId) throws Exception {
        wsNotificationService.notify(reservationService.lockReservableSchedule(reservableScheduleId));
        return ResponseEntity.ok().build();
    }

   //@PostMapping("/reserve")

    //@DeleteMapping("/delete")


}
