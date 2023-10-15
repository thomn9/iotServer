package cz.reservation.app.service;

import cz.reservation.app.model.dto.ReservableScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSNotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notify(ReservableScheduleDto reservableScheduleDto) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/reservable-schedule",reservableScheduleDto);
    }

}