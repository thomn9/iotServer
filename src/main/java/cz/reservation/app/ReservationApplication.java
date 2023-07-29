package cz.reservation.app;

import cz.reservation.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ReservationApplication {

	@Autowired
	private ReservationService reservationService;

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void generateReservableSchedule() {
		reservationService.createReservableSchedule(1L);
		reservationService.createReservableSchedule(2L);
	}

}