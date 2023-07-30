package cz.reservation.app.model;

import cz.reservation.app.model.dto.ReservationBaseDto;
import lombok.Data;

@Data
public class WsRequest {
    private WsAction wsAction;

    private Long reservableScheduleId;

    private String reservationCode;

    private ReservationBaseDto reservationBaseDto;

}
