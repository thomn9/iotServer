package cz.reservation.app.listener;

import cz.reservation.app.model.entity.ReservableSchedule;
import cz.reservation.app.model.event.ReservableScheduleEvent;
import cz.reservation.app.service.ReservableScheduleService;
import cz.reservation.app.service.WSNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class Listener {

    @Autowired
    private ReservableScheduleService reservableScheduleService;

    @Autowired
    private WSNotificationService wsNotificationService;

    @EventListener(ApplicationReadyEvent.class)
    public void generateReservableSchedule() {
        reservableScheduleService.createReservableSchedule(1L);
        reservableScheduleService.createReservableSchedule(2L);
    }

    @TransactionalEventListener
    public void handleReservableScheduleEvent(ReservableScheduleEvent reservableScheduleEvent) throws Exception {
        wsNotificationService.notify(reservableScheduleEvent.getReservableScheduleUpdateEventDtoList());
    }

}
