package cz.reservation.app;

import cz.reservation.app.service.ReservableScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ReservationApplication {

	@Autowired
	private ReservableScheduleService reservableScheduleService;

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void generateReservableSchedule() {
		reservableScheduleService.createReservableSchedule(1L);
		reservableScheduleService.createReservableSchedule(2L);
	}

}