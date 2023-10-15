package cz.reservation.app.model.event;

import cz.reservation.app.model.dto.ReservableScheduleUpdateEventDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservableScheduleEvent {
    private List<ReservableScheduleUpdateEventDto> reservableScheduleUpdateEventDtoList;
}
