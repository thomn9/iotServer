package cz.reservation.app.controller;

import cz.reservation.app.model.dto.ReservableScheduleBaseDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservableScheduleWebSocketController {

    @MessageMapping("/lock")
    @SendTo("/topic/reservable-schedule")
    public List<ReservableScheduleBaseDto> lock(Long reservableScheduleId) throws Exception {
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @MessageMapping("/reserve")
    @SendTo("/topic/reservable-schedule")
    public List<ReservableScheduleBaseDto> reserve(Long reservableScheduleId) throws Exception {
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @MessageMapping("/delete-reservation")
    @SendTo("/topic/reservable-schedule")
    public List<ReservableScheduleBaseDto> deleteReservation(String reservationCode) throws Exception {
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}