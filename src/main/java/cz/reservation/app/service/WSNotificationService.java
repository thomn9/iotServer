package cz.reservation.app.service;

import cz.reservation.app.model.dto.ReservableScheduleUpdateEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WSNotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notify(List<ReservableScheduleUpdateEventDto> reservableScheduleUpdateEventDtoList) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/reservable-schedule",reservableScheduleUpdateEventDtoList);
    }

}