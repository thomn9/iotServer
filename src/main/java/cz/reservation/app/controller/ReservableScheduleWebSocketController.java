package cz.reservation.app.controller;

import cz.reservation.app.ErrorCode;
import cz.reservation.app.model.WsRequest;
import cz.reservation.app.model.dto.ReservableScheduleDto;
import cz.reservation.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ReservableScheduleWebSocketController {

    @Autowired
    private ReservationService reservationService;

    @MessageMapping("/reservation")
    @SendTo("/topic/reservable-schedule")
    public ReservableScheduleDto wsRequestHandle(WsRequest wsRequest) throws Exception {

        switch (wsRequest.getWsAction()) {
            case LOCK:
                return reservationService.lockReservableSchedule(wsRequest.getReservableScheduleId());
            case DELETE:
                return reservationService.deleteReservation(wsRequest.getReservationCode());
            case RESERVE:
                return reservationService.createReservation(wsRequest.getReservationBaseDto());
            default:
                throw new Exception(ErrorCode.UNKNOWN_WS_ACTION.getKey());
        }

    }

}