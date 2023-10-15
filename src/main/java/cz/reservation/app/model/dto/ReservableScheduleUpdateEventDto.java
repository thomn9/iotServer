package cz.reservation.app.model.dto;

import cz.reservation.app.model.ReservableState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservableScheduleUpdateEventDto {
    private Long id;
    private ReservableState newReservableState;
}
